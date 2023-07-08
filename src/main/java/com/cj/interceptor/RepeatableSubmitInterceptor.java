package com.cj.interceptor;

import com.cj.annotation.RepeatSubmit;
import com.cj.request.RepeatableReadRequestWrapper;
import com.cj.util.RedisCache;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Date 2023/7/8 8:58
 * @Author cc
 */
@Component
public class RepeatableSubmitInterceptor implements HandlerInterceptor {
    /**
     *
     */

    /**
     * 请求的参数  key
     */
    public static final String REPEAT_PARAMS = "repeat_params";
    /**
     * 请求的当前时间  key
     */
    public String REPEAT_TIME = "repeat_time";
    /**
     * 存入 redis的key ，需要带 令牌+url，，， 每个人的key都是不同的
     */
    public String HEADER = "Authorization";

    private final String REPEAT_SUBMIT_KEY = "repeat_submit_key";

    @Autowired
    RedisCache redisCache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // HandlerMethod 、？？
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
            if (annotation != null){
                // 需要判断是不是重复提交
                int interval = annotation.interval();
                // 重复提交

                // 请求参数
                String nowParams="";
                if (request instanceof RepeatableReadRequestWrapper){
                    // json提交
                    RepeatableReadRequestWrapper repeatableReadRequestWrapper = (RepeatableReadRequestWrapper) request;
                    nowParams = repeatableReadRequestWrapper.getReader().readLine();
                }

                if (StringUtils.isEmpty(nowParams)){
                    nowParams = new ObjectMapper().writeValueAsString(request.getParameterMap());
                }

                Map<String,Object> map = new HashMap<>();
                long time1 = System.currentTimeMillis();

                //
                HashMap<String, Object> nowDataMap = new HashMap<>();
                nowDataMap.put(REPEAT_PARAMS,nowParams);
                nowDataMap.put(REPEAT_TIME, time1);
                String requestURI = request.getRequestURI();
                String header = request.getHeader(HEADER);

                String cacheKey = REPEAT_SUBMIT_KEY+requestURI+header.replace("Bearer ","");

                // 存入的是个map
                Object cacheObj = redisCache.getCacheObj(cacheKey);
                if (cacheObj !=null){
                    Map<String,Object> map1 = (Map<String,Object>) cacheObj;

                    // 比较参数  和  时间

                    // 不是重复提交
                    String dataParams = (String) map1.get(REPEAT_PARAMS);
                    boolean equalParams = dataParams.equals(nowParams);

                    Long time2 = (Long) map1.get(REPEAT_TIME);
                    boolean isRepeatTime  = (time1-time2) < annotation.interval();

                    if (!equalParams ||  !isRepeatTime){
                       return  true;
                    }

                    //
                }else{
                    redisCache.setCacheObj(cacheKey,nowDataMap,annotation.interval(), TimeUnit.MILLISECONDS);
                    return true;
                }
                // 是重复提交


                map.put("status",500);
                map.put("message",annotation.message());
                response.setContentType("application/json;character=utf-8");
                response.getWriter().write(new ObjectMapper().writeValueAsString(map));
                return false;
            }
//            boolean b = handlerMethod.hasMethodAnnotation(RepeatSubmit.class);
        }

        // 判断这个请求是否处理过
        String s = request.getReader().readLine();
        System.out.println("s = " + s);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
