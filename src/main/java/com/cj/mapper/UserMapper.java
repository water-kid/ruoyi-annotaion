package com.cj.mapper;

import com.cj.entity.Role;
import com.cj.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author cc
 * @since 2023-07-08
 */
public interface UserMapper extends BaseMapper<User> {

    List<Role> getRolesByUid(Long userId);

    List<User> getAllUser(User user);
}
