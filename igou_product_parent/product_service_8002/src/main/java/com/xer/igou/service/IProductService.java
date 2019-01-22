package com.xer.igou.service;

import com.xer.igou.domain.Product;
import com.baomidou.mybatisplus.service.IService;
import com.xer.igou.domain.Specification;
import com.xer.igou.query.ProductQuery;
import com.xer.igou.util.PageList;

import java.util.HashMap;

/**
 * <p>
 * 商品 服务类
 * </p>
 *
 * @author xer
 * @since 2019-01-13
 */
public interface IProductService extends IService<Product> {

    /**
     * 关联查询获取关联对象属性
     * @param query
     * @return
     */
    PageList<Product> selectPageList(ProductQuery query);

    void saveViewProperties(HashMap<String,Object> specifications);

    void saveSkuProperties(HashMap<String, Object> specifications);

    /**
     * 商品上下架操作
     * @param params
     */
    void onSaleOrOffSale(HashMap<String, Object> params);
}
