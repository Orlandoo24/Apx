package com.hqzl.apx.game.pojo;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class MyPetDto {
    private Integer userPetId;
    private String name;
    private String imgUrl;
    private Integer iqLevel;
    private Integer iqValue;
    private Integer iqValueMax;
    private Integer satietyMax;
    private Integer satietyValue;
    private Integer luckValue;
    private Integer active;
}
