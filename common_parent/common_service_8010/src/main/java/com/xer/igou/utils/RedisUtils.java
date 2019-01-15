package com.xer.igou.utils;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public enum  RedisUtils {
    /**
     * 实例
     */
    INSTANCE;
    private static JedisPool pool = null;

    static {
        //创建连接池配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        //设置最小连接数
        config.setMaxIdle(2);
        //设置最大连接数
        config.setMaxTotal(10);
        //设置最大等待时间
        config.setMaxWaitMillis(2*10000);
        //设置连接是否畅通
        config.setTestOnBorrow(true);

        //通过配置对象创建连接池对象
        pool = new JedisPool(config,"127.0.0.1",6379);
    }

    //获取连接对象
    public Jedis getResource() {
        return pool.getResource();
    }
    //释放连接对象
    public void releaseResource(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    public void set(String key, String value) {
        Jedis jedis = new Jedis();
        jedis.set(key,value);
        releaseResource(jedis);
    }

    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = new Jedis();
            return jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            releaseResource(jedis);
        }
    }

    public void clear() {
        Jedis jedis = null;
        try {
            jedis = new Jedis();
            jedis.flushAll();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            releaseResource(jedis);
        }
    }
}
