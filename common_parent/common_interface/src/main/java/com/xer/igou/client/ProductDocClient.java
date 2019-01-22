package com.xer.igou.client;

import com.xer.igou.util.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "COMMON-SERVICE",fallbackFactory = ProductDocClientFallbackFactory.class )
public interface ProductDocClient {

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    AjaxResult add(@RequestBody ProductDoc prodcutDoc);

    @RequestMapping(value = "/addBatch",method = RequestMethod.POST)
    AjaxResult addBatch(@RequestBody List<ProductDoc> prodcutDocs);

    @RequestMapping(value = "/del",method = RequestMethod.DELETE)
    AjaxResult del(@RequestParam(value = "id") Long id);
    @RequestMapping(value = "/delBatch",method = RequestMethod.POST)
    AjaxResult delBatch(@RequestBody List<ProductDoc> ids);

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    ProductDoc get(@RequestParam(value = "id") Long id);

    @RequestMapping(value = "/getList",method = RequestMethod.GET)
    List<ProductDoc> getList();
}
