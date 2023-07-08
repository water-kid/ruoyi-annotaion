package com.cj.aspect;

import com.cj.annotation.RateLimiter;
import com.cj.enums.LimitType;
import com.cj.exception.RateLimitException;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

/**
 * @Date 2023/7/6 20:48
 * @Author cc
 */
@Component
@Aspect
public class RateLimitAspect {

    private static  final Logger logger = LoggerFactory.getLogger(RateLimitAspect.class);

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RedisScript<Long> redisScript;

    /**
     * 拦截 RateLimiter 这个注解
     * @param joinpoint
     * @param rateLimiter
     */
    @Before("@annotation(rateLimiter)")
    public void before(JoinPoint joinpoint, RateLimiter rateLimiter) throws RateLimitException {
        int time = rateLimiter.time();
        int count = rateLimiter.count();

        String combineKey = getCombineKey(joinpoint, rateLimiter);
        try {
            Long number = (Long) redisTemplate.execute(redisScript, Collections.singletonList(combineKey), time, count);


            if (number == null || number > count){
                logger.info("当前接口已达到最大限流次数");
                throw new RateLimitException("访问过于频繁，请稍后访问");
            }
            logger.info("一个时间窗内请求次数是{}，当前请求次数是{},缓存的key为{}",count,number,combineKey);
        } catch (Exception e) {
            throw e;
        }

//        String combineKey =
    }



    private String getCombineKey(JoinPoint joinPoint,RateLimiter rateLimiter){
        StringBuffer sb = new StringBuffer(rateLimiter.key());
        if (rateLimiter.limitType()== LimitType.IP){
            // 工具类获取ip地址
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            sb.append("ip:").append("-");
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        String className = signature.getDeclaringType().getName();

        sb.append(className).append("-").append(methodName);

        return sb.toString();
    }

}
