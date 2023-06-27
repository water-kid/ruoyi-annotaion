package com.cj.datasource;

/**
 * @Date 2023/6/27 21:10
 * @Author cc
 */
public class DynamicDataSourceContextHolder {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    public static String getDsName(){
        return threadLocal.get();
    }

    public static void setDsName(String dsName){
        threadLocal.set(dsName);
    }


    public static void clearDsName(){
        threadLocal.remove();
    }
}
