package com.hqzl.apx.mbg.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserPet {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer petId;
    private Integer iqLevel;
    private Integer iqValue;
    private Integer satietyValue;
    private Integer luckValue;
    private Integer active;
    private Integer satietySpeed;
    private Integer satietyMax;
    private Long lastTime;
    private Long nextTime;
}
