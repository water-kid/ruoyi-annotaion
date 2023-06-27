package com.cj;

import com.cj.datasource.LoadDataSource;
import com.cj.mapper.UserMapper;
import com.cj.model.User;
import com.cj.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class RuoyiAnnotationApplicationTests {

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
        List<User> users = userService.getAllUser();
    }

    @Autowired
    LoadDataSource loadDataSource;

    @Test
    public void test(){
        System.out.println(loadDataSource);
    }
}
