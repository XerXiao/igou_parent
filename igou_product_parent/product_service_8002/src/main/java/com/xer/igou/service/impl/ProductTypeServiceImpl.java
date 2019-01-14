package com.xer.igou.service.impl;

import com.xer.igou.domain.ProductType;
import com.xer.igou.mapper.ProductMapper;
import com.xer.igou.mapper.ProductTypeMapper;
import com.xer.igou.service.IProductService;
import com.xer.igou.service.IProductTypeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 商品目录 服务实现类
 * </p>
 *
 * @author xer
 * @since 2019-01-13
 */
@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements IProductTypeService {

    @Autowired
    private ProductTypeMapper productTypeMapper;

    @Override
    public List<ProductType> getDataTree() {

            //查询所有的类别信息
            List<ProductType> productTypes = productTypeMapper.selectList(null);
            //创建map建立id与实体之间的关系，之后可以在循环中直接通过id拿到对象
            HashMap<Long, ProductType> map = new HashMap<>();
            //遍历集合建立更关系
            for (ProductType productType : productTypes) {
                map.put(productType.getId(),productType);
            }
            ArrayList<ProductType> result = new ArrayList<>();
            for (ProductType productType : productTypes) {
                if(productType.getPid() == 0) {
                    result.add(productType);
                }else {
                    ProductType p = map.get(productType.getPid());
                    if(p.getChildren() == null) {
                        p.setChildren(new ArrayList<ProductType>());
                    }
                    p.getChildren().add(productType);
                }
            }
            return result;
    }
}
