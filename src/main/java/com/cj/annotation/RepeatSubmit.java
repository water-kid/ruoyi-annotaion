package com.cj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 防止重复提交
 * @Date 2023/7/8 8:55
 * @Author cc
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RepeatSubmit {

    /**
     * 两个请求  之间的间隔时间
     */
    int interval() default 5000;

    /**
     * 重复提交 消息提示
     */
    String message() default "不允许重复提交";

}
