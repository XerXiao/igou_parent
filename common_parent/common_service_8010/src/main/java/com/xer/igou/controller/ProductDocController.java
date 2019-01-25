package com.xer.igou.controller;

import com.xer.igou.index.ProductDoc;
import com.xer.igou.client.ProductDocClient;
import com.xer.igou.serviec.IProductDocService;
import com.xer.igou.util.AjaxResult;
import com.xer.igou.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public PageList<Map<String,Object>> search(@RequestBody Map<String,Object> map) {
        return productDocService.search(map);
    }
}
