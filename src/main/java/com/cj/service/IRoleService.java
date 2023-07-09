package com.cj.service;

import com.cj.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author cc
 * @since 2023-07-08
 */
public interface IRoleService extends IService<Role> {

    List<Role> getAllRoles(Role role);
}
