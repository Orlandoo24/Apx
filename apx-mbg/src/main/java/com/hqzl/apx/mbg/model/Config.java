package com.hqzl.apx.mbg.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class Config {
    private Integer id;
    @TableField(value = "`key`")
    private String key;
    @TableField(value = "`value`")
    private String value;
    private String note;
}
