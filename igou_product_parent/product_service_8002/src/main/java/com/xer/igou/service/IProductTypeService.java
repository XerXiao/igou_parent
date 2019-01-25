package com.xer.igou.service;

import com.xer.igou.domain.Brand;
import com.xer.igou.domain.ProductType;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

    /**
     * 添加子标签
     * @param productType
     */
    void insertChild(ProductType productType);

    /**
     * 获取导航面包屑
     * @param productTypeId
     * @return
     */
    List<Map<String,Object>> getCrumbles(Long productTypeId);

    /**
     * 获取品牌信息
     * @param productTypeId
     * @return
     */
    List<Brand> getBrands(Long productTypeId);

    /**
     * 获取品牌首字母
     * @param productTypeId
     * @return
     */
    Set<String> getBrandsFirstLetters(Long productTypeId);
}
