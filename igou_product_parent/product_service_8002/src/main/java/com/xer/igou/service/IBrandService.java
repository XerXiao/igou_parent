package com.xer.igou.service;

import com.xer.igou.domain.Brand;
import com.baomidou.mybatisplus.service.IService;
import com.xer.igou.domain.Product;
import com.xer.igou.domain.ProductType;
import com.xer.igou.query.BrandQuery;
import com.xer.igou.query.ProductQuery;
import com.xer.igou.util.PageList;

import java.util.List;

/**
 * <p>
 * 品牌信息 服务类
 * </p>
 *
 * @author xer
 * @since 2019-01-13
 */
public interface IBrandService extends IService<Brand> {

    /**
     * 获取品牌信息
     * @return
     */
    List<Brand> getDataTree();
    /**
     * 关联查询获取关联对象属性
     * @param query
     * @return
     */
    PageList<Brand> selectPageList(BrandQuery query);
}
