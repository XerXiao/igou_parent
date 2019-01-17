package com.xer.igou.mapper;

import com.xer.igou.domain.ProductType;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 商品目录 Mapper 接口
 * </p>
 *
 * @author xer
 * @since 2019-01-13
 */
public interface ProductTypeMapper extends BaseMapper<ProductType> {

    /**
     * 插入子节点
     * @param productType
     */
    void insertChildren(ProductType productType);
}
