package com.cj;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.cj.datasource.LoadDataSource;
import com.cj.mapper.UserMapper;
import com.cj.entity.User;
import com.cj.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Types;
import java.util.Collections;
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

    @Value("${spring.datasource.ds.master.url}")
    private String url;
    @Test
    public void test02(){
        System.out.println("url = " + url);

        FastAutoGenerator.create(url, "root", "root")
                .globalConfig(builder -> {
                    builder.author("cc") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
//                            .fileOverride() // 覆盖已生成文件
                            .outputDir("I:\\ruoyi-annotation\\src\\main\\java"); // 指定输出目录
                })

                .packageConfig(builder -> {
                    builder.parent("com.cj") // 设置父包名
//                            .moduleName("system") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "I:\\ruoyi-annotation\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
//
                    builder.addInclude("sys_user","sys_role","sys_dept").addTablePrefix("sys_")
                            .entityBuilder().enableFileOverride()
                            .serviceBuilder().enableFileOverride()
                            .mapperBuilder().enableFileOverride()
                            .controllerBuilder().enableFileOverride();

                })

                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
