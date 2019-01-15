package com.xer.igou.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(value = "COMMON-SERVICE",fallbackFactory = PageClientFallbackFactory.class )
public interface PageClient {

    /**
     * 根据传入的特定数据和特定模板生成特定页面到特定位置
     * @param model
     * @param templateFile
     * @param targetFile
     */
    @RequestMapping(value = "/staticIndexPage", method = RequestMethod.POST)
    void staticIndexPage(@RequestBody Map<String,Object> params);
}
