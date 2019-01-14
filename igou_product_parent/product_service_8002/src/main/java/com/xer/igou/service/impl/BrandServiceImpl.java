package com.xer.igou.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.xer.igou.domain.Brand;
import com.xer.igou.domain.Product;
import com.xer.igou.domain.ProductType;
import com.xer.igou.mapper.BrandMapper;
import com.xer.igou.query.BrandQuery;
import com.xer.igou.service.IBrandService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xer.igou.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 品牌信息 服务实现类
 * </p>
 *
 * @author xer
 * @since 2019-01-13
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IBrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<Brand> getDataTree() {
        return brandMapper.selectList(null);
    }

    @Override
    public PageList<Brand> selectPageList(BrandQuery query) {
        Page<Brand> page = new Page<>(query.getPage(),query.getRows());

        List<Brand> rows = brandMapper.getAllBrands(page, query);

        long total = page.getTotal();
        return new PageList<>(total,rows);
    }
}
