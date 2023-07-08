package com.cj.enums;

/**
 * 限流的类型
 * @Date 2023/7/6 20:07
 * @Author cc
 */
public enum LimitType {
    /**
     * 接口   ： 这个接口一分钟访问多少次
     */
    DEFAULT,
    /**
     * 针对 ip
     */
    IP
}
