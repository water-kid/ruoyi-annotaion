package com.cj.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2023/7/8 19:05
 * @Author cc
 */
@Data
public class BaseEntity {
    @TableField(exist = false)
    private Map<String,String> params = new HashMap<>();
}
