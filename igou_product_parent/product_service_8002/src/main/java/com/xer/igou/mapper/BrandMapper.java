package com.xer.igou.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.xer.igou.domain.Brand;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xer.igou.query.BrandQuery;

import java.util.List;

/**
 * <p>
 * 品牌信息 Mapper 接口
 * </p>
 *
 * @author xer
 * @since 2019-01-13
 */
public interface BrandMapper extends BaseMapper<Brand> {

    /**
     * 获取品牌信息
     * @param page
     * @param query
     * @return
     */
    List<Brand> getAllBrands(Page<Brand> page, BrandQuery query);
}
