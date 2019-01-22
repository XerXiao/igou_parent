package com.xer.igou.controller;

import com.xer.igou.client.ProductDoc;
import com.xer.igou.client.ProductDocClient;
import com.xer.igou.serviec.IProductDocService;
import com.xer.igou.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductDocController implements ProductDocClient {

    @Autowired
    private IProductDocService productDocService;

    @Override
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public AjaxResult add(@RequestBody ProductDoc productDoc) {
        try {
            productDocService.add(productDoc);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("保存失败"+e.getMessage());
        }
    }

    @Override
    @RequestMapping(value = "/addBatch",method = RequestMethod.POST)
    public AjaxResult addBatch(@RequestBody List<ProductDoc> productDocs) {
        try {
            productDocService.addBatch(productDocs);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("保存失败"+e.getMessage());
        }
    }

    @Override
    @RequestMapping(value = "/del",method = RequestMethod.DELETE)
    public AjaxResult del(@RequestParam(value = "id") Long id) {
        try {
            productDocService.del(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除失败"+e.getMessage());
        }
    }


    @Override
    @RequestMapping(value = "/delBatch",method = RequestMethod.POST)
    public AjaxResult delBatch(@RequestBody List<ProductDoc> ids) {
        try {
            productDocService.delBatch(ids);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除失败"+e.getMessage());
        }
    }

    @Override
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public ProductDoc get(@RequestParam(value = "id") Long id) {
        return productDocService.get(id);
    }

    @Override
    @RequestMapping(value = "/getList",method = RequestMethod.GET)
    public List<ProductDoc> getList() {
        return productDocService.getList();
    }
}
