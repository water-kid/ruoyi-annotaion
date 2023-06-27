package com.cj.aspect;

import com.cj.datasource.DynamicDataSourceContextHolder;
import com.cj.annotation.DataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Date 2023/6/27 21:13
 * @Author cc
 */
@Aspect
@Component
public class DataSourceAspect {
    /**
     * @annotation(com.cj.annotation.DataSource) : 拦截方法上的注解
     *  @within(com.cj.annotation.DataSource)： 类上面有注解，将类中的方法拦截下来
     */
    @Pointcut("@annotation(com.cj.annotation.DataSource) || @within(com.cj.annotation.DataSource)")
    public void pointcut(){}

    /**
     * 清除threadlocal
     * @return
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp){
        DataSource dataSource = this.getDataSource(pjp);
        if (dataSource!=null){
            String dsName = dataSource.value();
            DynamicDataSourceContextHolder.setDsName(dsName);
        }

        try {
            return pjp.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            DynamicDataSourceContextHolder.clearDsName();
        }
    }

    public DataSource getDataSource(ProceedingJoinPoint pjp){
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        DataSource annotation = method.getAnnotation(DataSource.class);
        if (annotation==null){
            annotation = (DataSource) signature.getDeclaringType().getAnnotation(DataSource.class);
        }

        return annotation;
    }
}
