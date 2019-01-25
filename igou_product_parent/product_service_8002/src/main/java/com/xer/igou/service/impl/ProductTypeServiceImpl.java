package com.xer.igou.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xer.igou.client.PageClient;
import com.xer.igou.client.RedisClient;
import com.xer.igou.domain.Brand;
import com.xer.igou.domain.Product;
import com.xer.igou.domain.ProductType;
import com.xer.igou.mapper.BrandMapper;
import com.xer.igou.mapper.ProductTypeMapper;
import com.xer.igou.service.IProductTypeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

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

    @Autowired
    private BrandMapper brandMapper;



    @Override
    public List<Brand> getBrands(Long productTypeId) {
        return brandMapper.selectList(new EntityWrapper<Brand>().eq("product_type_id",productTypeId));
    }

    @Override
    public Set<String> getBrandsFirstLetters(Long productTypeId) {
        List<Brand> list = brandMapper.selectList(new EntityWrapper<Brand>().eq("product_type_id", productTypeId));
        Set<String> letters = new TreeSet<>();
        list.forEach(brand -> {
            letters.add(brand.getFirstLetter());
        });
        return letters;
    }


    @Override
    public List<Map<String,Object>> getCrumbles(Long productTypeId) {

        //获取商品类别信息
        ProductType productType = productTypeMapper.selectById(productTypeId);
        //获取到path
        String path = productType.getPath();
        //分解path获取到其他同级以及父类信息
        path = path.substring(1, path.length() - 1);
        String[] split = path.split("\\.");
        Long[] longs = new Long[split.length];
        if(split.length != 0) {
            for (int i = 0; i < split.length; i++) {
                longs[i] = Long.parseLong(split[i]);
            }
        }
        List<Long> ids = Arrays.asList(longs);

        ArrayList< Map<String, Object>> list = new ArrayList<>();
        for (Long id : ids) {
            Map<String,Object> node = new HashMap<>();
            //获取每一个节点信息
            //先获取自己
            ProductType self = productTypeMapper.selectById(id);
            node.put("productTypeSelf",self);
            //获取兄弟节点
            Long pid = self.getPid();
            List<ProductType> brothers =
                    productTypeMapper.selectList(new EntityWrapper<ProductType>()
                            .eq("pid", pid));
            //去掉自己
            Iterator<ProductType> iterator = brothers.iterator();
            while (iterator.hasNext()) {
                ProductType me = iterator.next();
                if(me.getId().equals(self.getId())) {
                    iterator.remove();
                    break;
                }
            }
            //放入兄弟节点
            node.put("productTypeBrothers",brothers);
            list.add(node);
        }
        return list;
    }




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

    @Override
    public void insertChild(ProductType productType) {
        try {

            productTypeMapper.insertChildren(productType);
            StringBuffer sb = new StringBuffer();
            //获取到id，拼接path
            //处理拼接path
            String path = productType.getPath();
            if(path == null) {
                path = ".";
            }
            Long id = productType.getId();
            //.pid.pid.id.
            sb.append(path).append(id).append(".");
            productType.setPath(sb.toString());
            productTypeMapper.updateById(productType);
            synchronizeOperate();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                if(p != null) {
                    if ( p.getChildren() == null) {
                        p.setChildren(new ArrayList<ProductType>());
                    }
                    p.getChildren().add(productType);
                }
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
    public boolean deleteBatchIds(Collection<? extends Serializable> idList) {
        try {
            super.deleteBatchIds(idList);
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
            properties.load(this.getClass().getClassLoader().getResourceAsStream("staticPage.properties"));
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
