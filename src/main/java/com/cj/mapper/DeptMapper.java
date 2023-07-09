package com.cj.mapper;

import com.cj.entity.Dept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author cc
 * @since 2023-07-08
 */
public interface DeptMapper extends BaseMapper<Dept> {

    List<Dept> getAllDepts(Dept dept);
}
