package com.xer.igou.controller;

import com.sun.org.apache.xpath.internal.SourceTree;
import com.xer.igou.client.ProductDocClient;
import com.xer.igou.domain.Product;
import com.xer.igou.domain.Specification;
import com.xer.igou.query.ProductQuery;
import com.xer.igou.service.IProductService;
import com.xer.igou.util.AjaxResult;
import com.xer.igou.util.PageList;
import com.xer.igou.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    public IProductService productService;


    @Autowired
    private ProductDocClient productDocClient;
    /**
     * 前台主页查询过滤
     * @param query
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public PageList<Map<String,Object>> search(@RequestBody Map<String,Object> query) {
        //keyword productTypeId brandId price sortField sortType page rows maxPrice minPrice
        //从ES中查询获取数据
        return productDocClient.search(query);
    }


    /**
     * 保存和修改公用的
     *
     * @param product 传递的实体
     * @return Ajaxresult转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AjaxResult save(@RequestBody Product product) {
        try {
            if (product.getId() != null) {
                productService.updateById(product);
            } else {
                productService.insert(product);
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
            productService.deleteById(id);
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
    @RequestMapping(value = "/batchRemove",method = RequestMethod.DELETE)
    public AjaxResult batchRemove(String ids) {
        try {
            List<Long> longs = StrUtils.splitStr2LongArr(ids);
            productService.deleteBatchIds(longs);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！" + e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product get(@PathVariable(value = "id", required = true) Long id) {
        return productService.selectById(id);
    }


    /**
     * 查看所有的员工信息
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Product> list() {

        return productService.selectList(null);
    }


    /**
     * 分页查询数据
     *
     * @param query 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/json", method = RequestMethod.POST)
    public PageList<Product> json(@RequestBody ProductQuery query) {

        return productService.selectPageList(query);

    }

    @RequestMapping(value = "saveViewProperties",method = RequestMethod.POST)
    public AjaxResult saveViewProperties(@RequestBody HashMap<String,Object> specifications) {
        try {
            productService.saveViewProperties(specifications);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存对象失败！" + e.getMessage());
        }

    }
    @RequestMapping(value = "saveSkuProperties",method = RequestMethod.POST)
    public AjaxResult saveSkuProperties(@RequestBody HashMap<String,Object> skus) {
        try {
            productService.saveSkuProperties(skus);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存对象失败！" + e.getMessage());
        }

    }


    @RequestMapping(value = "onSaleOrOff",method = RequestMethod.POST)
    public AjaxResult onSaleOrOff(@RequestBody HashMap<String,Object> params) {
        try {
            productService.onSaleOrOffSale(params);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("上下架操作失败！" + e.getMessage());
        }
    }



}
