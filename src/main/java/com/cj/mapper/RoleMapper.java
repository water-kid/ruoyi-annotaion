package com.cj.mapper;

import com.cj.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author cc
 * @since 2023-07-08
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> getAllRoles(Role role);
}
