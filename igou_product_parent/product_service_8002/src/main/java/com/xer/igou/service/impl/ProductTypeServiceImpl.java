package com.xer.igou.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xer.igou.client.PageClient;
import com.xer.igou.client.RedisClient;
import com.xer.igou.domain.ProductType;
import com.xer.igou.mapper.ProductTypeMapper;
import com.xer.igou.service.IProductTypeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * <p>
 * 商品目录 服务实现类
 * </p>
 *
 * @author xer
 * @since 2019-01-13
 */
@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements IProductTypeService {

    public static final String REDIS_STR = "producttype_in_redis";

    @Autowired
    private RedisClient redisClient;
    @Autowired
    private PageClient pageClient;
    @Autowired
    private ProductTypeMapper productTypeMapper;

    @Override
    public List<ProductType> getDataTree() {

        ArrayList<ProductType> result = null;
        //从缓存获取数据
        String productTypeStr = redisClient.get(REDIS_STR);
        if (StringUtils.isNotBlank(productTypeStr)) {
            //缓存中有对应数据
            //直接返回
            return JSONArray.parseArray(productTypeStr, ProductType.class);
        } else {
            //缓存中没有，从数据库查询
            //查询所有的类别信息
            List<ProductType> productTypes = productTypeMapper.selectList(null);
            result = loopGetProductTypes(productTypes);
        }
        return result;
    }

    private ArrayList<ProductType> loopGetProductTypes(List<ProductType> productTypes) {
        ArrayList<ProductType> result;//创建map建立id与实体之间的关系，之后可以在循环中直接通过id拿到对象
        HashMap<Long, ProductType> map = new HashMap<>();
        //遍历集合建立更关系
        for (ProductType productType : productTypes) {
            map.put(productType.getId(), productType);
        }
        result = new ArrayList<>();
        for (ProductType productType : productTypes) {
            if (productType.getPid() == 0) {
                result.add(productType);
            } else {
                ProductType p = map.get(productType.getPid());
                if (p.getChildren() == null) {
                    p.setChildren(new ArrayList<ProductType>());
                }
                p.getChildren().add(productType);
            }
        }
        return result;
    }

    @Override
    public boolean insert(ProductType entity) {
        try {
            super.insert(entity);
            synchronizeOperate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteById(Serializable id) {
        try {
            super.deleteById(id);
            synchronizeOperate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateById(ProductType entity) {
        try {
            super.updateById(entity);
            synchronizeOperate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 数据修改之后同步数据库与缓存数据
     */
    private void synchronizeOperate() {
        //获取所有数据
        //查询所有的类别信息
        List<ProductType> productTypes = productTypeMapper.selectList(null);
        ArrayList<ProductType> list = loopGetProductTypes(productTypes);

        //清空对应缓存
        //同步数据
        String productTypeStr = JSONArray.toJSONString(list);
        redisClient.set(REDIS_STR,productTypeStr);

        //根据修改后数据重新生成静态化页面
        HashMap<String, Object> pageParams = new HashMap<>();
        //读取配置文件获取配置信息
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("sataticPage.properties"));
            pageParams.put("model",list);
            pageParams.put("templateFile",properties.getProperty("staticPageTemplateFile"));
            pageParams.put("targetFile",properties.getProperty("staticPageTargetPath"));
            pageClient.staticIndexPage(pageParams);

            //更新主页静态化页面
            HashMap<String, Object> indexParams = new HashMap<>();
            HashMap<String, Object> staticRoot = new HashMap<>();
            staticRoot.put("staticRoot",properties.getProperty("staticIndexPageStaticRoot"));
            indexParams.put("model",staticRoot);
            indexParams.put("templateFile",properties.getProperty("staticIndexPageTemplateFile"));
            indexParams.put("targetFile",properties.getProperty("staticIndexPageTargetPath"));
            pageClient.staticIndexPage(indexParams);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
