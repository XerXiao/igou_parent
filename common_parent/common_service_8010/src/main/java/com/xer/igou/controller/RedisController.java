package com.xer.igou.controller;

import com.xer.igou.client.RedisClient;
import com.xer.igou.utils.RedisUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController implements RedisClient {

    @RequestMapping(value = "/redis", method = RequestMethod.POST,produces = "application/json")
    @Override
    public void set(@RequestParam(value = "key") String key, @RequestParam(value = "value") String value) {
        RedisUtils.INSTANCE.set(key, value);
    }

    @RequestMapping(value = "/redis", method = RequestMethod.GET)
    @Override
    public String get(@RequestParam(value = "key") String key) {
        return RedisUtils.INSTANCE.get(key);
    }

    @RequestMapping(value = "/clear", method = RequestMethod.GET)
    @Override
    public void clear() {
        RedisUtils.INSTANCE.clear();
    }
}
