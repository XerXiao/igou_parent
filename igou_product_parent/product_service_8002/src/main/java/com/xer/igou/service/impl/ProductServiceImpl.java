package com.xer.igou.service.impl;

import com.xer.igou.domain.Product;
import com.xer.igou.mapper.ProductMapper;
import com.xer.igou.service.IProductService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author xer
 * @since 2019-01-12
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
