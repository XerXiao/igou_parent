package com.xer.igou.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xer.igou.client.RedisClient;
import com.xer.igou.domain.Product;
import com.xer.igou.mapper.ProductMapper;
import com.xer.igou.query.ProductQuery;
import com.xer.igou.service.IProductService;
import com.xer.igou.util.PageList;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
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
    @Autowired
    private RedisClient redisClient;

    @Autowired
    private ProductMapper productMapper;

    /**
     * 使用缓存
     * @param query
     * @return
     */
    private static long total;
    @Override
    public PageList<Product> selectPageList(ProductQuery query) {
        List<Product> rows = null;
        Page<Product> page = null;

        //高级查询从数据库获取值
//        if(!"".equals(query.getKeyword()) && query.getKeyword() != null) {
//            System.out.println("高级查询获取");
//            String allProducts_in_redis = redisClient.get(KEY_IN_REDIS);
//            page = new Page<>(query.getPage(),query.getRows());
//            rows = productMapper.getAllProducts(page, query);
//            long total = page.getTotal();
//            return new PageList<>(total,rows);
//        }
        //从缓存中获取
        String products_in_redis = redisClient.get(KEY_IN_REDIS+query.getPage());
        if(StringUtils.isNotBlank(products_in_redis)) {
            //如果有对应值
            System.out.println("第"+query.getPage()+"页缓存获取");
            //转换为数组返回
            rows = JSONArray.parseArray(products_in_redis, Product.class);
        }else {
            System.out.println("第"+query.getPage()+"页数据库获取");
            page = new Page<>(query.getPage(),query.getRows());
            //缓存中没有,到数据库中查询
            rows = productMapper.getAllProducts(page, query);
            total = page.getTotal();
            //同步更新缓存
            String redisStr = JSONArray.toJSONString(rows);
            redisClient.set(KEY_IN_REDIS+query.getPage(),redisStr);
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
        productMapper.insert(entity);
        redisClient.clear();
        return super.insert(entity);
    }

    @Override
    public boolean deleteById(Serializable id) {
        productMapper.deleteById(id);
        redisClient.clear();
        return super.deleteById(id);
    }

    @Override
    public boolean updateById(Product entity) {
        productMapper.updateById(entity);
        redisClient.clear();
        return super.updateById(entity);
    }

    @Override
    public boolean deleteBatchIds(Collection<? extends Serializable> idList) {
        productMapper.deleteBatchIds(idList);
        redisClient.clear();
        return super.deleteBatchIds(idList);
    }
}
