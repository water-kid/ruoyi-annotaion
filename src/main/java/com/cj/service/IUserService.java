package com.cj.service;

import com.cj.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author cc
 * @since 2023-07-08
 */
public interface IUserService extends IService<User> {

    List<User> getAllUser(User user);
}
