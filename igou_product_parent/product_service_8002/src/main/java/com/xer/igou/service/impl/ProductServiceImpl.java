package com.xer.igou.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xer.igou.domain.Product;
import com.xer.igou.domain.ProductType;
import com.xer.igou.mapper.ProductMapper;
import com.xer.igou.query.ProductQuery;
import com.xer.igou.service.IProductService;
import com.xer.igou.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author xer
 * @since 2019-01-13
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public PageList<Product> selectPageList(ProductQuery query) {
        Page<Product> page = new Page<>(query.getPage(),query.getRows());

        List<Product> rows = productMapper.getAllProducts(page, query);

        long total = page.getTotal();
        return new PageList<>(total,rows);
    }


}
