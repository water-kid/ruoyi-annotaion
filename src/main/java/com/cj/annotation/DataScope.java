package com.cj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 根据当前用户的 role ，， 去查对应的部门，，如果你找的那个表有 dept_id 就根据那个表的 dept_id 过滤
 * 如果你找的那个表没有dept_id，，就需要关联user表，，根据user表上的dept_id过滤，，就需要关联user表，，给user表取别名，，比如查找sys_role的时候
 * @Date 2023/7/8 19:42
 * @Author cc
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataScope {

    /**
     * 部门表的  别名
     */
    String deptAlias()  default "";

    /**
     * user 表的  别名
     */
    String userAlias() default "";
}
