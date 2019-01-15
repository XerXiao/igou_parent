package com.xer.igou.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "COMMON-SERVICE",fallbackFactory = RedisClientFallbackFactory.class )
public interface RedisClient {

    @RequestMapping(value = "/redis", method = RequestMethod.POST,produces = "application/json")
    void set(@RequestParam(value = "key") String key, @RequestParam(value = "value") String value);

    @RequestMapping(value = "/redis", method = RequestMethod.GET)
    String get(@RequestParam(value = "key") String key);

    @RequestMapping(value = "/clear", method = RequestMethod.GET)
    void clear();
}
