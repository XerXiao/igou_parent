package com.xer.igou.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "COMMON-SERVICE",fallbackFactory = RedisClientFallbackFactory.class )
public interface RedisClient {

    /**
     * 存储数据到redis中
     * @param key
     * @param value
     */
    @RequestMapping(value = "/redis", method = RequestMethod.POST,produces = "application/json")
    void set(@RequestParam(value = "key") String key, @RequestParam(value = "value") String value);

    /**
     * 从redis中获取对应键值的数据
     * @param key
     * @return
     */
    @RequestMapping(value = "/redis", method = RequestMethod.GET)
    String get(@RequestParam(value = "key") String key);

    /**
     * 清空所有缓存
     */
    @RequestMapping(value = "/clear", method = RequestMethod.GET)
    void clear();
}
