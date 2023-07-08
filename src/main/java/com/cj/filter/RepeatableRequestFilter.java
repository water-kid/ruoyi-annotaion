package com.cj.filter;

import com.cj.request.RepeatableReadRequestWrapper;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Date 2023/7/8 9:21
 * @Author cc
 */
public class RepeatableRequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        request.getContentType()
        // 请求是json
        if (StringUtils.startsWithIgnoreCase(request.getContentType(),"application/json")){
            RepeatableReadRequestWrapper repeatableReadRequestWrapper = new RepeatableReadRequestWrapper((HttpServletRequest) request, (HttpServletResponse) response);
            chain.doFilter(repeatableReadRequestWrapper,response);
            return;
        }

        chain.doFilter(request,response);
    }
}
