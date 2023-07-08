package com.cj.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;

/**
 * @Date 2023/7/8 10:31
 * @Author cc
 */
@Component
@Aspect
public class TestHandlerMethodAspect {

    public void pointcut(){}

    @Before("execution(* com.cj.controller.*.*(..))")
    public void beforeExecution(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        HandlerMethod handlerMethod = new HandlerMethod(joinPoint.getTarget(), method);
        boolean b = handlerMethod.hasMethodAnnotation(GetMapping.class);
        System.out.println("b111111111 = " + b);

    }
}
