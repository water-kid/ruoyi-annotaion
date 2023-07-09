package com.cj.service;

import com.cj.entity.Dept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author cc
 * @since 2023-07-08
 */
public interface IDeptService extends IService<Dept> {

    List<Dept> getAllDepts(Dept dept);
}
