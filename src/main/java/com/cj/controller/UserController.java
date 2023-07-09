package com.cj.controller;

import com.cj.annotation.DataScope;
import com.cj.entity.User;
import com.cj.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author cc
 * @since 2023-07-08
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping
    @DataScope(deptAlias = "d",userAlias = "u")
    public List<User> getAllUser(User user){
        return userService.getAllUser(user);
    }
}
