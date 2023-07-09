package com.cj.service.impl;

import com.cj.entity.Dept;
import com.cj.mapper.DeptMapper;
import com.cj.service.IDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author cc
 * @since 2023-07-08
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {
    @Autowired
    DeptMapper deptMapper;

    @Override
    public List<Dept> getAllDepts(Dept dept) {
        return  deptMapper.getAllDepts(dept);
    }
}
