package com.hqzl.apx.mbg.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Prop {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String imgUrl;
    private String name;
    private Integer type;

    // 消耗品才有
    private Integer iqValueBonus;
    private Integer luckValueBonus;
    private Integer satietyValueBonus;


    // 装备才有
    private String iqValueAdd;
    private String balanceAdd;
    private Integer luckValueAdd;
    private Integer satietyMaxAdd;
    private Integer satietySpeedAdd;
    private String price;
    private Date updateTime;
    private Date createTime;
}
