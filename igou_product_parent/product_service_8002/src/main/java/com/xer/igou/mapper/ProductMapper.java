package com.xer.igou.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xer.igou.domain.Product;
import com.xer.igou.query.ProductQuery;

import java.util.List;

/**
 * <p>
 * 商品 Mapper 接口
 * </p>
 *
 * @author xer
 * @since 2019-01-13
 */
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 获取商品信息
     * @param page
     * @param query
     * @return
     */
    List<Product> getAllProducts(Page<Product> page, ProductQuery query);

}
