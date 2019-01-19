package com.xer.igou.mapper;

import com.xer.igou.domain.Specification;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 商品属性 Mapper 接口
 * </p>
 *
 * @author xer
 * @since 2019-01-19
 */
public interface SpecificationMapper extends BaseMapper<Specification> {

    List<Specification> selectViewListByTypeId(Long typeId);

    List<Specification> selectSkuListByTypeId(Long typeId);
}
