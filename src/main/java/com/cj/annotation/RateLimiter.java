package com.cj.annotation;

import com.cj.enums.LimitType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Date 2023/7/6 20:03
 * @Author cc
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RateLimiter {
    /**
     * 限流的 key。。。。  限流的 前缀 ..在redis中
     * @return
     */
    String key() default "rate_limit";

    /**
     * 限流时间窗  60s
     * @return
     */
    int time() default 60;

    /**
     * 在时间窗内的 限流次数
     * @return
     */
    int count() default 3;

    /**
     * 限流类型
     */
    LimitType limitType() default LimitType.DEFAULT;
}
