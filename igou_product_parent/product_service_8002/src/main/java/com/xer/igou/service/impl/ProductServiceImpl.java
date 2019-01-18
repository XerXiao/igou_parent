package com.xer.igou.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xer.igou.client.RedisClient;
import com.xer.igou.domain.Product;
import com.xer.igou.domain.ProductExt;
import com.xer.igou.mapper.ProductExtMapper;
import com.xer.igou.mapper.ProductMapper;
import com.xer.igou.query.ProductQuery;
import com.xer.igou.service.IProductService;
import com.xer.igou.util.PageList;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author xer
 * @since 2019-01-13
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    public static final String KEY_IN_REDIS = "products_in_redis";
    public static final String TOTAL_IN_REDIS = "total_in_redis";
    @Autowired
    private RedisClient redisClient;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductExtMapper productExtMapper;
    /**
     * 使用缓存
     * @param query
     * @return
     */
    @Override
    public PageList<Product> selectPageList(ProductQuery query) {
        List<Product> rows = null;
        Page<Product> page = null;
        Long total = 0L;

        //高级查询从数据库获取值
        if(!"".equals(query.getKeyword()) && query.getKeyword() != null) {
//            System.out.println("高级查询获取");
            page = new Page<>(0,query.getRows());
            rows = productMapper.getAllProducts(page, query);
            total = page.getTotal();
            return new PageList<>(total,rows);
        }
        //从缓存中获取
        String products_in_redis = redisClient.get(KEY_IN_REDIS+query.getPage());
        if(StringUtils.isNotBlank(products_in_redis)) {
            //如果有对应值
//            System.out.println("第"+query.getPage()+"页缓存获取");
            //转换为数组返回
            rows = JSONArray.parseArray(products_in_redis, Product.class);
            //拿到对应总条数
            total = Long.parseLong(redisClient.get(TOTAL_IN_REDIS));
        }else {
//            System.out.println("第"+query.getPage()+"页数据库获取");
            page = new Page<>(query.getPage(),query.getRows());
            //缓存中没有,到数据库中查询
            rows = productMapper.getAllProducts(page, query);
            total = page.getTotal();
            //同步更新缓存
            String redisStr = JSONArray.toJSONString(rows);
            redisClient.set(KEY_IN_REDIS+query.getPage(),redisStr);
            total = page.getTotal();
            redisClient.set(TOTAL_IN_REDIS,total.toString());
        }
        return new PageList<>(total,rows);
    }


    /**
     *
     * 覆写修改 增删改操作
     */

    @Override
    public boolean insert(Product entity) {
        //执行操作后清空缓存
        try {
            //先将产品信息保存
            productMapper.insertProcut(entity);

            //保存关联表信息
            entity.getProductExt().setProductId(entity.getId());
            productExtMapper.insert(entity.getProductExt());
            redisClient.clear();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteById(Serializable id) {
        try {
            productMapper.deleteById(id);

            //商品与商品扩展之间是一对一关系需要通过商品id查出对应的扩展信息进行修改
            EntityWrapper<ProductExt> wrapper = new EntityWrapper<>();
            wrapper.eq("productId",id);
            List<ProductExt> productExts = productExtMapper.selectList(wrapper);
            if (!productExts.isEmpty()) {
                productExtMapper.deleteById(productExts.get(0).getId());
            }
            redisClient.clear();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateById(Product entity) {
        try {
            entity.setUpdateTime(new Date());
            productMapper.updateById(entity);

            //商品与商品扩展之间是一对一关系需要通过商品id查出对应的扩展信息进行修改
            EntityWrapper<ProductExt> wrapper = new EntityWrapper<>();
            wrapper.eq("productId",entity.getId());
            List<ProductExt> productExts = productExtMapper.selectList(wrapper);
            if (!productExts.isEmpty()) {
                ProductExt productExt = productExts.get(0);
                productExt.setProductId(entity.getId());
                entity.getProductExt().setId(productExt.getId());
                productExtMapper.updateById(entity.getProductExt());
            }
            redisClient.clear();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteBatchIds(Collection<? extends Serializable> idList) {
        try {
            productMapper.deleteBatchIds(idList);

            ArrayList<Long> longs = new ArrayList<>();
            for (Serializable serializable : idList) {
                EntityWrapper<ProductExt> wrapper = new EntityWrapper<>();
                wrapper.eq("productId",serializable);
                List<ProductExt> productExts = productExtMapper.selectList(wrapper);
                if(!productExts.isEmpty()) {
                    longs.add(productExts.get(0).getId());
                }
            }
            productExtMapper.deleteBatchIds(longs);
            redisClient.clear();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
