package com.xer.igou.service.impl;

import java.io.IOException;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xer.igou.client.PageClient;
import com.xer.igou.index.ProductDoc;
import com.xer.igou.client.ProductDocClient;
import com.xer.igou.client.RedisClient;
import com.xer.igou.domain.*;
import com.xer.igou.mapper.*;
import com.xer.igou.query.ProductQuery;
import com.xer.igou.service.IProductService;
import com.xer.igou.service.IProductTypeService;
import com.xer.igou.util.PageList;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

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

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private ProductDocClient productDocClient;

    @Autowired
    private ProductTypeMapper productTypeMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private PageClient pageClient;

    @Autowired
    private IProductTypeService productTypeService;

    /**
     * 使用缓存
     *
     * @param query
     * @return
     */
    @Override
    public PageList<Product> selectPageList(ProductQuery query) {
        List<Product> rows = null;
        Page<Product> page = null;
        Long total = 0L;

        //高级查询从数据库获取值
        if (!"".equals(query.getKeyword()) && query.getKeyword() != null) {
//            System.out.println("高级查询获取");
            page = new Page<>(0, query.getRows());
            rows = productMapper.getAllProducts(page, query);
            total = page.getTotal();
            return new PageList<>(total, rows);
        }
        //从缓存中获取
        String products_in_redis = redisClient.get(KEY_IN_REDIS + query.getPage());
        if (StringUtils.isNotBlank(products_in_redis)) {
            //如果有对应值
//            System.out.println("第"+query.getPage()+"页缓存获取");
            //转换为数组返回
            rows = JSONArray.parseArray(products_in_redis, Product.class);
            //拿到对应总条数
            total = Long.parseLong(redisClient.get(TOTAL_IN_REDIS));
        } else {
//            System.out.println("第"+query.getPage()+"页数据库获取");
            page = new Page<>(query.getPage(), query.getRows());
            //缓存中没有,到数据库中查询
            rows = productMapper.getAllProducts(page, query);
            total = page.getTotal();
            //同步更新缓存
            String redisStr = JSONArray.toJSONString(rows);
            redisClient.set(KEY_IN_REDIS + query.getPage(), redisStr);
            total = page.getTotal();
            redisClient.set(TOTAL_IN_REDIS, total.toString());
        }
        return new PageList<>(total, rows);
    }

    @Override
    public void saveViewProperties(HashMap<String, Object> specifications) {
        Integer productId = (Integer) specifications.get("productId");

        List<Specification> properties = (List<Specification>) specifications.get("properties");

        String pro = JSON.toJSONString(properties);

        Product product = productMapper.selectById(productId);
        product.setViewProperties(pro);

        productMapper.updateById(product);

    }

    @Override
    public void saveSkuProperties(HashMap<String, Object> specifications) {
        Integer productId = (Integer) specifications.get("productId");

        List<Specification> skuProperties = (List<Specification>) specifications.get("skuProperties");
        List<Map<String, Object>> skus = (List<Map<String, Object>>) specifications.get("skuPro");
        String pro = JSON.toJSONString(skuProperties);

        Product product = productMapper.selectById(productId);
        product.setSkuTemplate(pro);


        //获取该商品之前的sku全部清除
        List<Sku> products = skuMapper.selectList(new EntityWrapper<Sku>().eq("productId", productId));
        if(!products.isEmpty()) {
            List<Long> list = new ArrayList<>();
            for (Sku sku : products) {
                list.add(sku.getId());
            }
            skuMapper.deleteBatchIds(list);
        }
        //设置标志值获取最高最低价格
        int min = 999999;
        int max = 0;
        for (Map<String, Object> sku : skus) {
            Set<String> keys = sku.keySet();
            StringBuffer stringBuffer = new StringBuffer();
            Sku skuObj = new Sku();
            skuObj.setProductId(productId.longValue());
            StringBuffer idSb = new StringBuffer();


            for (String key : keys) {
                switch (key) {
                    case "price":
                        int price = Integer.valueOf(sku.get(key).toString());
                        min = price < (min) ? price : min;
                        max = price > (max) ? price : max;

                        skuObj.setPrice(price*100);
                        break;
                    case "availableStock":
                        skuObj.setAvailableStock(Integer.valueOf(sku.get(key).toString()));
                        break;
                    case "state":
                        if ("true".equals(sku.get(key).toString())) {
                            skuObj.setState(true);
                        } else {
                            skuObj.setState(false);
                        }
                        break;
                    default:

                        String[] split = key.split(":");
                        //对应属性id
                        Long id = Long.parseLong(split[0]);
                        //值
                        String value = (String) sku.get(key);

                        //获取每个属性对应值的id
                        int valuesId = getValuesId(id, value, skuProperties);
                        idSb.append(valuesId).append("_");
                        stringBuffer.append(key + ":").append(value + "_");
                }
            }
            skuObj.setSkuVlaues(stringBuffer.toString().substring(0, stringBuffer.toString().lastIndexOf("_")));
            //保存数据到sku表
            skuObj.setIndex(idSb.toString().substring(0, idSb.toString().lastIndexOf("_")));

            skuMapper.insert(skuObj);
        }

        product.setMinPrice(min*100);
        product.setMaxPrice(max*100);
        //        修改商品
        productMapper.updateById(product);

        //如果商品处于上架状态需要同步更新ES
        if(product.getState() == 1) {
            ProductDoc productDoc = product2productDoc(product);
            productDocClient.add(productDoc);
        }
        redisClient.clear();

    }

    @Override
    public void onSaleOrOffSale(HashMap<String, Object> params) {
        Integer onSale = (Integer) params.get("onSale");

        String ids = (String) params.get("ids");
        String[] productIds = ids.split(",");
        Long[] LongIds = new Long[productIds.length];
        for (int i = 0; i < productIds.length; i++) {
            LongIds[i] = Long.parseLong(productIds[i]);
        }
        List<Long> longs = Arrays.asList(LongIds);

        //保存修改数据库
        if (onSale == 1) {
            //下架操作
            HashMap<String, Object> map = new HashMap<>();
            map.put("offSaleTime", new Date());
            map.put("ids", longs);
            productMapper.offSale(map);

            List<ProductDoc> productDocs = new ArrayList<>();
            for (Long aLong : longs) {
                Product product = productMapper.selectById(aLong);
                if (product != null) {
                    productDocs.add(product2ProductDocList(product));
                }
            }
            productDocClient.delBatch(productDocs);
            redisClient.clear();
        } else {
            //上架操作
            HashMap<String, Object> map = new HashMap<>();
            map.put("onSaleTime", new Date());
            map.put("ids", longs);
            productMapper.onSale(map);
            List<ProductDoc> productDocs = new ArrayList<>();
            for (Long aLong : longs) {
                Product product = productMapper.selectById(aLong);
                if (product != null) {
                    productDocs.add(product2ProductDocList(product));
                }
            }
            //插入es库
            productDocClient.addBatch(productDocs);

            //同步生成静态页面
            //读取配置文件获取配置信息
            Properties properties = new Properties();
            try {
                properties.load(this.getClass().getClassLoader().getResourceAsStream("staticPage.properties"));
                for (ProductDoc productDoc : productDocs) {
                    HashMap<String, Object> indexParams = new HashMap<>();
                    HashMap<String, Object> staticRoot = new HashMap<>();
                    //更新详情页静态化页面
                    staticRoot.put("staticRoot",properties.getProperty("staticIndexPageStaticRoot"));
                    //放入数据：类别面包屑、商品信息、规格参数、商品详情
                    //获取商品类别面包屑
                    List<Map<String, Object>> crumbles = productTypeService.getCrumbles(productDoc.getProductTypeId());
                    staticRoot.put("crumbles",crumbles);
                    //商品信息
                    staticRoot.put("product",productDoc);
                    //规格参数
                    List<Specification> specifications = JSON.parseArray(productDoc.getViewProperties(), Specification.class);
                    staticRoot.put("viewProperties",specifications);
                    //商品详情
                    List<ProductExt> productExts = productExtMapper.selectList(new EntityWrapper<ProductExt>().eq("productId", productDoc.getId()));
                    if(productExts != null && !productExts.isEmpty()) {
                        ProductExt productExt = productExts.get(0);
                        staticRoot.put("richContent",productExt.getRichContent());
                    }



                    //sku属性选项
                    Product product = productMapper.selectById(productDoc.getId());

                    List<Specification> skuOptions = JSON.parseArray(product.getSkuTemplate(), Specification.class);
                    if(skuOptions != null && !skuOptions.isEmpty()) {
                        staticRoot.put("skuPropertiesLength",skuOptions.size());
                        staticRoot.put("skuProperties",skuOptions);
                    }

                    //sku具体
                    List<Sku> skus = skuMapper.selectList(new EntityWrapper<Sku>().eq("productId", productDoc.getId()));
                    if(skus != null && !skus.isEmpty()) {
                        for (Sku sku : skus) {
                            sku.setSkuVlaues(null);
                        }
                    }
                    staticRoot.put("skus",JSON.toJSONString(skus));

                    indexParams.put("model",staticRoot);
                    indexParams.put("templateFile",properties.getProperty("staticDetailPageTemplateFile"));
                    indexParams.put("targetFile","E:/igou_web_parent/igou_shopping/pages/"+productDoc.getId()+".html");
                    pageClient.staticIndexPage(indexParams);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            redisClient.clear();
        }


    }

    private ProductDoc product2ProductDocList(Product product) {
        return product2productDoc(product);
    }

    private int getValuesId(Long id, String value, List<Specification> skuProperties) {
        String s = JSON.toJSONString(skuProperties);
        List<Specification> specifications = JSON.parseArray(s, Specification.class);
        for (Specification skuProperty : specifications) {
            if (id.equals(skuProperty.getId())) {
                //拿到skuValus数组
                List<String> valuse = skuProperty.getSkuValues();
                //拿到对应值所在的下标
                return valuse.indexOf(value);
            }
        }
        return -1;
    }


    /**
     * 覆写修改 增删改操作
     */

    @Override
    public boolean insert(Product entity) {
        //执行操作后清空缓存
        try {
            //先将产品信息保存
            productMapper.insertProcut(entity);

            //插入es库
            productDocClient.add(product2productDoc(entity));
            //保存关联表信息
            //富文本等大字段信息
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
            wrapper.eq("productId", id);
            List<ProductExt> productExts = productExtMapper.selectList(wrapper);
            if (!productExts.isEmpty()) {
                productExtMapper.deleteById(productExts.get(0).getId());
            }

            //如果商品为上架状态，需要同步删除es库
            Product product = productMapper.selectById(id);
            if (product.getState() == 1) {
                productDocClient.del(product.getId());
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
            wrapper.eq("productId", entity.getId());
            List<ProductExt> productExts = productExtMapper.selectList(wrapper);
            if (!productExts.isEmpty()) {
                ProductExt productExt = productExts.get(0);
                productExt.setProductId(entity.getId());
                entity.getProductExt().setId(productExt.getId());
                productExtMapper.updateById(entity.getProductExt());
            }
            //如果商品为上架状态，要同步修改es
            if (entity.getState() == 1) {
                ProductDoc productDoc = product2productDoc(entity);
                productDocClient.add(productDoc);
            }
            redisClient.clear();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 商品转换为索引库商品对象
     *
     * @param entity
     * @return
     */
    private ProductDoc product2productDoc(Product entity) {

        ProductDoc productDoc = new ProductDoc();
        productDoc.setId(entity.getId());
        productDoc.setName(entity.getName());
        productDoc.setProductTypeId(entity.getProductTypeId());
        productDoc.setBrandId(entity.getBrandId());
        productDoc.setOnsaleTime(new Date());
        productDoc.setSaleCount(entity.getSaleCount());
        productDoc.setViewCount(entity.getViewCount());
        productDoc.setCommentCount(entity.getCommentCount());

        ProductType productType = productTypeMapper.selectById(entity.getProductTypeId());
        Brand brand = brandMapper.selectById(entity.getBrandId());

        productDoc.setAll(entity.getName() + " " + entity.getSubName() + " " + productType.getName() + " " + brand.getName());
        productDoc.setViewProperties(entity.getViewProperties());
        productDoc.setSkuProperties(entity.getSkuTemplate());
        String media = entity.getMedia();
        List<String> medias = null;
        if (StringUtils.isNotBlank(media)) {
            String[] split = media.split(",");
            medias = Arrays.asList(split);
        }
        productDoc.setMedias(medias);

//        //获取sku遍历得到所有价格获取最大最小值
//        List<Sku> skus = skuMapper.selectList(new EntityWrapper<Sku>().eq("productId", entity.getId()));
//        if (!skus.isEmpty()) {
//            Integer min = skus.get(0).getPrice();
//            Integer max = skus.get(0).getPrice();
//            for (Sku sku : skus) {
//                if (sku.getPrice() < min) {
//                    min = sku.getPrice();
//                }
//                if (sku.getPrice() > max) {
//                    max = sku.getPrice();
//                }
//            }
//            productDoc.setMaxPrice(max);
//            productDoc.setMinPrice(min);
//        } else {
//            productDoc.setMaxPrice(0);
//            productDoc.setMinPrice(0);
//        }
        productDoc.setMinPrice(entity.getMinPrice());
        productDoc.setMaxPrice(entity.getMaxPrice());
        return productDoc;
    }

    @Override
    public boolean deleteBatchIds(Collection<? extends Serializable> idList) {
        try {
            productMapper.deleteBatchIds(idList);

            ArrayList<Long> longs = new ArrayList<>();
            for (Serializable serializable : idList) {
                EntityWrapper<ProductExt> wrapper = new EntityWrapper<>();
                wrapper.eq("productId", serializable);
                List<ProductExt> productExts = productExtMapper.selectList(wrapper);
                if (!productExts.isEmpty()) {
                    longs.add(productExts.get(0).getId());
                }
            }
            productExtMapper.deleteBatchIds(longs);

            //如果商品为上架状态要同步删除es库中的数据
//            List<Long> ids = new ArrayList<>();
//            idList.forEach(id-> {
//                Product product = productMapper.selectById(id);
//                if(product != null) {
//                    if(product.getState() == 1) {
//                        ids.add(product.getId());
//                    }
//                }
//            });
            List<ProductDoc> productDocs = new ArrayList<>();
            for (Long aLong : longs) {
                Product product = productMapper.selectById(aLong);
                if (product != null) {
                    productDocs.add(product2ProductDocList(product));
                }
            }
            productDocClient.delBatch(productDocs);
            redisClient.clear();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
