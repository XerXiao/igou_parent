package com.xer.igou.client;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Component
public class PageClientFallbackFactory implements FallbackFactory<PageClient>{


    @Override
    public PageClient create(Throwable throwable) {
        return new PageClient() {
            @Override
            public void staticIndexPage(@RequestBody Map<String,Object> params) {
                System.out.println("....");
            }
        };
    }
}
