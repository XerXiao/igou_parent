package com.xer.igou.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.xer.igou.domain.Specification;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xer.igou.query.SpecificationQuery;

import java.util.List;

/**
 * <p>
 * 商品属性 Mapper 接口
 * </p>
 *
 * @author xer
 * @since 2019-01-18
 */
public interface SpecificationMapper extends BaseMapper<Specification> {

    List<Specification> getAllSpecifications(Page<Specification> page, SpecificationQuery query);
}
