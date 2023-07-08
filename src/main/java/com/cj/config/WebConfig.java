package com.cj.config;

import com.cj.filter.RepeatableRequestFilter;
import com.cj.interceptor.RepeatableSubmitInterceptor;
import com.cj.interceptor.TestHandlerMethodInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

/**
 * @Date 2023/7/8 9:01
 * @Author cc
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    RepeatableSubmitInterceptor repeatableSubmitInterceptor;

    @Autowired
    TestHandlerMethodInterceptor testHandlerMethodInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(repeatableSubmitInterceptor).addPathPatterns("/**");
        registry.addInterceptor(testHandlerMethodInterceptor).addPathPatterns("/**");
    }

    @Bean
    public FilterRegistrationBean<RepeatableRequestFilter> customFilterRegistration(){
        FilterRegistrationBean<RepeatableRequestFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new RepeatableRequestFilter());
        // 过滤所有请求    /** 不匹配
        filterFilterRegistrationBean.addUrlPatterns("/*");
        return filterFilterRegistrationBean;
    }
}
