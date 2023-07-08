package com.cj.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Date 2023/7/8 10:18
 * @Author cc
 */
@Component
public class TestHandlerMethodInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        System.out.println(method.getName());
        // 获取 控制器类名
        System.out.println(handlerMethod.getBeanType().getName());

        // 是否有这个注解
        boolean b = handlerMethod.hasMethodAnnotation(Repeatable.class);
        System.out.println("b = " + b);

        // 获取所有注解
        Annotation[] annotations = method.getAnnotations();
        System.out.println(Arrays.toString(annotations));

        // 获取参数上的注解，，，一个参数可能有多个注解
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (Annotation[] parameterAnnotation : parameterAnnotations) {
            System.out.println(Arrays.toString(parameterAnnotation));
        }

        return true;
    }
}
