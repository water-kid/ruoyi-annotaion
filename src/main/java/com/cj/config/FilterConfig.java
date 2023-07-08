package com.cj.config;

import com.cj.filter.CustomFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Date 2023/7/8 9:39
 * @Author cc
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<CustomFilter> filterFilterRegistrationBean(){
        FilterRegistrationBean<CustomFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CustomFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
