package com.cj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cj.entity.Role;
import com.cj.entity.User;
import com.cj.mapper.UserMapper;
import com.cj.service.IRoleService;
import com.cj.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author cc
 * @since 2023-07-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService, UserDetailsService {


    @Override
    public List<User> getAllUser(User user) {
        return userMapper.getAllUser(user);
    }

    @Autowired
    UserMapper userMapper;

    @Autowired
    IRoleService roleService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        userMapper.get
        User user = getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        if (user == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        // 设置权限
       List<Role> roleList = userMapper.getRolesByUid(user.getUserId());
        user.setRoleList(roleList);


        return user;
    }


}
