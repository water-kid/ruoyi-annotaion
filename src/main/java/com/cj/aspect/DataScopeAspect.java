package com.cj.aspect;

import com.cj.annotation.DataScope;
import com.cj.entity.BaseEntity;
import com.cj.entity.Role;
import com.cj.entity.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

/**
 * 动态权限控制   ： 根据数据库中 sys_role中的 data_scope的值，来动态权限
 *   1： 不设置权限控制
 *   2：  自定义权限：  根据数据库连表查出来的权限，，在sys_role_dept  中
 *   3：  只能查自己的部门，，  在sys_user中有自己的部门字段
 *   4：  不仅可以查看自己的，，还可以查看自己的子部门，，， 在 sys_dept中找出 自己部门和子部门
 *   5：  不能查看部门，，，只能看自己的数据
 *
 *   遍历角色，，根据 sys_role 判断每个角色具有的 data_scope ，拼接成 sql，
 * @Date 2023/7/8 19:45
 * @Author cc
 */
@Component
@Aspect
public class DataScopeAspect {

    public static final String DATA_SCOPE_ALL = "1";
    // 自定义权限
    public static final String DATA_SCOPE_CUSTOM = "2";
    // 只能查看自己的部门
    public static final String DATA_SCOPE_DEPT = "3";
    // 不仅能查看自己的，还能查看自己的子部门
    public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";
    // 只能查看自己的
    public static final String DATA_SCOPE_SELF = "5";
    // BaseEntity中的params Map  中 拼接sql的key
    public static final String DATA_SCOPE = "data_scope";


    /**
     * 前置通知，，，在查询之前给  BaseEntity中的params 添加 sql 过滤
     */

    @Before("@annotation(dataScope)")
    public void before(JoinPoint joinPoint, DataScope dataScope){
            // 这个 params 可能是前端传递的
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 前端传递的是 继承BaseEntity的对象,,并且只有一个参数
        Object arg = joinPoint.getArgs()[0];
        System.out.println("arg"+arg);
        if (arg != null && arg instanceof BaseEntity){
            BaseEntity baseEntity = (BaseEntity) arg;
            baseEntity.getParams().put(DATA_SCOPE,"");
        }



        // 获取当前用户信息
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 判断是不是admin
        if (user.getUserId() == 1L){
            // 不用过滤
            return;
        }

        /**
         *         select * from table_name  where  ... and (xxx or  xxx or xxx)
         *
         *         xxx： 每个role限制的条件
          */
        StringBuilder sql = new StringBuilder();
        List<Role> roleList = user.getRoleList();
        for (Role role : roleList) {
            // 根据角色，生成查询的sql

            //  每个角色对应的权限是不一样的，，

            // 获取角色对应的数据权限
            String ds = role.getDataScope();

            if (DATA_SCOPE_ALL.equals(ds)){
                // 查看所有数据
                return;
            }else if (DATA_SCOPE_CUSTOM.equals(ds)){
                // 自定义数据权限  ：  根据用户角色查找部门id
                sql.append(String.format(" OR  %s.dept_id in (select d.dept_id from sys_dept d,sys_role_dept rd where d.dept_id = rd.dept_id and rd.role_id =%d)",dataScope.deptAlias(),role.getRoleId()));

            }else if (DATA_SCOPE_DEPT.equals(ds)){
                // 自己当前部门
                sql.append(String.format(" OR %s.dept_id = %d",dataScope.deptAlias(),user.getDeptId()));
            }else if (DATA_SCOPE_DEPT_AND_CHILD.equals(ds)){
                // 自己部门和子部门
                sql.append(String.format(" OR %s.dept_id in (select d.dept_id from sys_dept d where d.dept_id = %d or find_in_set(%d,ancestors))",dataScope.deptAlias(),user.getDeptId(),user.getDeptId()));
            }else if(DATA_SCOPE_SELF.equals(ds)){
                // 看他传没有传 userAlias  。。 如果传了，表示那个表关联了 user表，，查找当前user表对应 userId 的数据，，如果没有 userAlias 表示没有连接 user表，
                // 没有部门权限，只能看自己的数据,,不能看部门数据
                String s = dataScope.userAlias();
                if ("".equals(s)){
                    // 没有连接 user表
                    sql.append(String.format(" OR %s.dept_id in (1=0)",dataScope.deptAlias()));
                }else{
                    // 连接了 user表
                    sql.append(String.format(" OR  %s.user_id= %d",dataScope.userAlias(),user.getUserId()));
                }
            }

            // 拼接 sql    and (xxx OR  xxx OR xxx)
            if (arg != null && arg instanceof BaseEntity){
                BaseEntity baseEntity = (BaseEntity) arg;
                baseEntity.getParams().put(DATA_SCOPE,"AND( "+ sql.substring(4) +" )");
            }

        }

    }
}
