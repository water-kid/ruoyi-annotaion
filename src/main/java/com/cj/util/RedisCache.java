package com.cj.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Date 2023/7/8 9:47
 * @Author cc
 */
@Component
public class RedisCache {
    @Autowired
    RedisTemplate redisTemplate;



    public<T> void setCacheObj(String key, T value, Integer timeout, TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key,value,timeout,timeUnit);
    }


    public <T> T getCacheObj(String key){
        ValueOperations<String,T> valueOperations = redisTemplate.opsForValue();
        T o = valueOperations.get(key);
        return o;
    }

}
