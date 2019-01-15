package com.xer.igou.controller;

import com.xer.igou.client.PageClient;
import com.xer.igou.utils.VelocityUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PageController implements PageClient{

    @RequestMapping(value = "/staticIndexPage", method = RequestMethod.POST)
    @Override
    public void staticIndexPage(@RequestBody Map<String,Object> params) {

        Object model = params.get("model");
        String templateFile = (String) params.get("templateFile");
        String targetFile = (String) params.get("targetFile");

        VelocityUtils.staticByTemplate(model,templateFile,targetFile);
    }
}
