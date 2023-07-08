package com.cj.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Date 2023/7/8 9:38
 * @Author cc
 */
public class CustomFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("hehe,.,");
        chain.doFilter(request,response);
    }
}
