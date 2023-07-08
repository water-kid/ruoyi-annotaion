package com.cj.exception;

import com.cj.annotation.RateLimiter;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2023/7/6 20:46
 * @Author cc
 */
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(RateLimitException.class)
    public Map<String,Object> rateLimitException(RateLimitException e){
        HashMap<String, Object> map = new HashMap<>();
        map.put("status",500);
        map.put("message",e.getMessage());
        return map;
    }
}
