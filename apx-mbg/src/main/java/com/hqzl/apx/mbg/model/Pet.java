package com.hqzl.apx.mbg.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Pet {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String imgUrl;
    private Integer satietyMax;
    private Integer satietySpeed;
    private Date updateTime;
    private Date createTime;
}
