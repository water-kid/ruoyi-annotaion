package com.cj.exception;

/**
 * @Date 2023/7/6 20:46
 * @Author cc
 */
public class RateLimitException extends Exception{


    public RateLimitException(String message) {
        super(message);
    }
}
