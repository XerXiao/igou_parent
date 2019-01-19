package com.xer.igou.service.impl;

import com.xer.igou.domain.Specification;
import com.xer.igou.mapper.SpecificationMapper;
import com.xer.igou.service.ISpecificationService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品属性 服务实现类
 * </p>
 *
 * @author xer
 * @since 2019-01-19
 */
@Service
public class SpecificationServiceImpl extends ServiceImpl<SpecificationMapper, Specification> implements ISpecificationService {

    @Autowired
    private SpecificationMapper specificationMapper;

    @Override
    public List<Specification> getListByTypeId(Long typeId) {

        //查询显示属性
        return specificationMapper.selectViewListByTypeId(typeId);
    }

    @Override
    public List<Specification> getSkuListByTypeId(Long typeId) {
        return specificationMapper.selectSkuListByTypeId(typeId);
    }
}
