package com.xer.igou.service;

import com.xer.igou.domain.ProductType;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 商品目录 服务类
 * </p>
 *
 * @author xer
 * @since 2019-01-13
 */
public interface IProductTypeService extends IService<ProductType> {

    /**
     * 获得分类信息树
     * * @return
     */
    List<ProductType> getDataTree();
}
