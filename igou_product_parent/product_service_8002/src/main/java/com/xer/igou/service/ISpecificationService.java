package com.xer.igou.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xer.igou.domain.Specification;
import com.baomidou.mybatisplus.service.IService;
import com.xer.igou.query.SpecificationQuery;
import com.xer.igou.util.PageList;

/**
 * <p>
 * 商品属性 服务类
 * </p>
 *
 * @author xer
 * @since 2019-01-18
 */
public interface ISpecificationService extends IService<Specification> {

    /**
     * 获取所有子属性选项
     * @param query
     * @return
     */
    PageList<Specification> selectPageList(SpecificationQuery query);
}
