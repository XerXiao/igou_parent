package com.xer.igou.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.xer.igou.domain.Product;
import com.xer.igou.service.IProductService;
import com.xer.igou.service.ISpecificationService;
import com.xer.igou.domain.Specification;
import com.xer.igou.query.SpecificationQuery;
import com.xer.igou.util.AjaxResult;
import com.xer.igou.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import com.xer.igou.util.StrUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/specification")
public class SpecificationController {
    @Autowired
    public ISpecificationService specificationService;
    @Autowired
    private IProductService productService;

    /**
    * 保存和修改公用的
    * @param specification  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody List<Specification> specification){
        try {
            specification.forEach(spec -> {
                if(spec.getId()!=null){
                    specificationService.updateById(spec);
                }else{
                    specificationService.insert(spec);
                }
            });
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存对象失败！"+e.getMessage());
        }
    }

    /**
    * 删除对象信息
    * @return
    */
    @RequestMapping(value="/delete",method=RequestMethod.DELETE)
    public AjaxResult delete(@RequestParam(value = "ids") String ids){
        try {

            specificationService.deleteBatchIds(StrUtils.splitStr2LongArr(ids));
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Specification get(@PathVariable(value="id",required=true) Long id)
    {
        return specificationService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/viewList/{productTypeId}",method = RequestMethod.GET)
    public List<Specification> viewList(@PathVariable(value="productTypeId",required = true) Long productTypeId){
        return specificationService.getListByTypeId(productTypeId);
    }

    @RequestMapping(value = "/skuList/{productTypeId}",method = RequestMethod.GET)
    public List<Specification> list(@PathVariable(value="productTypeId",required = true) Long productTypeId){

        return specificationService.getSkuListByTypeId(productTypeId);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<Specification> json(@RequestBody SpecificationQuery query)
    {
        Page<Specification> page = new Page<Specification>(query.getPage(),query.getRows());
            page = specificationService.selectPage(page);
            return new PageList<Specification>(page.getTotal(),page.getRecords());
    }

    /**
     * 根据类型id获取属性
     * type的id
     * is_sku:0 显示属性， 1 sku属性
     * @return
     */
    @RequestMapping(value = "/product/{id}",method = RequestMethod.GET)
    public List<Specification> getListByType(@PathVariable("id") Long productId) {


        //根据商品id获取商品
        Product product = productService.selectById(productId);
        String viewProperties = product.getViewProperties();
        if(StringUtils.isNotBlank(viewProperties)) {
            //已经设置了显示属性,回显
            return JSON.parseArray(viewProperties,Specification.class);
        }else {
//            System.out.println("查询 "+product.getProductTypeId());
            //没有设置查询
            return specificationService.getListByTypeId(product.getProductTypeId());
        }

    }

    @RequestMapping(value = "/productSku/{id}",method = RequestMethod.GET)
    public List<Specification> getSkuListByType(@PathVariable("id") Long productId) {
        //根据商品id获取商品
        Product product = productService.selectById(productId);
        String skuTemplate = product.getSkuTemplate();
        if(StringUtils.isNotBlank(skuTemplate)) {
            //已经设置了显示属性,回显属性配置信息
            return JSON.parseArray(skuTemplate,Specification.class);
        }else {
//            System.out.println("查询 "+product.getProductTypeId());
            //没有设置查询
            return specificationService.getSkuListByTypeId(product.getProductTypeId());
        }

    }
}
