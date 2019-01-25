package com.xer.igou.controller;

import com.alibaba.fastjson.JSON;
import com.xer.igou.domain.Brand;
import com.xer.igou.service.IProductTypeService;
import com.xer.igou.domain.ProductType;
import com.xer.igou.query.ProductTypeQuery;
import com.xer.igou.util.AjaxResult;
import com.xer.igou.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import com.xer.igou.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/productType")
public class ProductTypeController {
    @Autowired
    public IProductTypeService productTypeService;

    /**
     * 保存和修改公用的
     *
     * @param productType 传递的实体
     * @return Ajaxresult转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AjaxResult save(@RequestBody ProductType productType) {
        try {
            if (productType.getId() != null) {
                productTypeService.updateById(productType);
            } else {
                productTypeService.insertChild(productType);

            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存对象失败！" + e.getMessage());
        }
    }

    /**
     *
     * 批量添加子标签
     * @param productType
     * @return
     */
    @RequestMapping(value = "/saveBatch", method = RequestMethod.POST)
    public AjaxResult saveBatch(@RequestBody ProductType[] productType) {
        try {
            for (ProductType type : productType) {
                productTypeService.insertChild(type);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存对象失败！" + e.getMessage());
        }
    }

    /**
     * 删除对象信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public AjaxResult delete(@PathVariable("id") Long id) {
        try {
            productTypeService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！" + e.getMessage());
        }
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/deleteBatch",method = RequestMethod.DELETE)
    public AjaxResult deleteBatch(@RequestParam(value = "ids") String ids){
        try {
            List<Long> longs = StrUtils.splitStr2LongArr(ids);
            productTypeService.deleteBatchIds(longs);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！" + e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ProductType get(@PathVariable(value = "id", required = true) Long id) {
        return productTypeService.selectById(id);
    }


    /**
     * 查看所有的分类信息
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<ProductType> list() {

        return productTypeService.selectList(null);
    }


    /**
     * 分页查询数据
     *
     * @param query 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/json", method = RequestMethod.POST)
    public PageList<ProductType> json(@RequestBody ProductTypeQuery query) {
        Page<ProductType> page = new Page<ProductType>(query.getPage(), query.getRows());
        page = productTypeService.selectPage(page);
        return new PageList<ProductType>(page.getTotal(), page.getRecords());
    }

    /**
     * 获取分类信息表
     *
     * @return
     */
    @RequestMapping(value = "/treeData", method = RequestMethod.GET)
    public List<ProductType> productTypeInfo() {
        return productTypeService.getDataTree();
    }

    /**
     * 获取面包屑
     */
    @RequestMapping(value = "/crumbles/{id}", method = RequestMethod.GET)
    public List<Map<String,Object>> crumbles(@PathVariable(value = "id", required = true) Long productTypeId) {
        return productTypeService.getCrumbles(productTypeId);
    }

    /**
     * 获取品牌信息
     * @param productTypeId
     * @return
     */
    @RequestMapping(value = "/brands/{id}", method = RequestMethod.GET)
    public List<Brand> brands(@PathVariable(value = "id", required = true) Long productTypeId) {
        return productTypeService.getBrands(productTypeId);
    }

    /**
     * 获取商品品牌首字母
     * @param productTypeId
     * @return
     */
    @RequestMapping(value = "/letters/{id}", method = RequestMethod.GET)
    public Set<String> letters(@PathVariable(value = "id", required = true) Long productTypeId) {
        return productTypeService.getBrandsFirstLetters(productTypeId);
    }
}
