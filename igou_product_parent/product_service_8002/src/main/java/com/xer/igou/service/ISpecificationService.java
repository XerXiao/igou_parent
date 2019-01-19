package com.xer.igou.service;

import com.xer.igou.domain.Specification;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 商品属性 服务类
 * </p>
 *
 * @author xer
 * @since 2019-01-19
 */
public interface ISpecificationService extends IService<Specification> {

    /**
     * 根据类型id获取对应属性
     * @param typeId
     * @return
     */
    List<Specification> getListByTypeId(Long typeId);

    List<Specification> getSkuListByTypeId(Long typeId);
}
