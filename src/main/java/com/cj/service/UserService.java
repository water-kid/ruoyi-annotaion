package com.cj.service;

import com.cj.annotation.DataSource;
import com.cj.mapper.UserMapper;
import com.cj.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Date 2023/6/27 23:44
 * @Author cc
 */
@Service
@DataSource("slave")

public class UserService {
    @Autowired
    UserMapper userMapper;
    @DataSource("master")
    public List<User> getAllUser(){
        List<User> users = userMapper.selectList(null);
        System.out.println("users = " + users);
        return users;
    }
}
