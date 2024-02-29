package com.hqzl.apx.game.pojo;

import com.hqzl.apx.game.pojo.MyProp;
import lombok.Data;

import java.util.List;
@Data
public class MyPetInfoResp {
    private Integer userPetId;
    private String petName;
    private String petImgUrl;
    private Integer iqLevel;
    private Integer iqValue;
    private Integer iqValueMax;
    private Integer satietyMax;
    private Integer satietyValue;
    private Integer luckValue;
    private Integer satietySpeed;
    private Integer active;

    private Long lastTime;
    private Long nextTime;


    // 宠物佩戴的装备
    private List<MyProp> myProps;
}