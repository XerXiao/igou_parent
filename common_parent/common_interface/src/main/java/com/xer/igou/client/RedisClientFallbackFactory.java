package com.xer.igou.client;


import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class RedisClientFallbackFactory implements FallbackFactory<RedisClient>{
    @Override
    public RedisClient create(Throwable throwable) {
        return new RedisClient() {
            @Override
            public void set(@RequestParam(value = "key") String key, @RequestParam(value = "value") String value) {

            }

            @Override
            public String get(@RequestParam(value = "key") String key) {

                return null;
            }

            @Override
            public void clear() {

            }
        };
    }
}
