package com.xer.igou.client;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class ProductDocClientFallbackFactory implements FallbackFactory<ProductDocClient>{
    @Override
    public ProductDocClient create(Throwable throwable) {
        return null;
    }
}
