package com.hqzl.apx.game.pojo;

import lombok.Data;

import java.util.List;


@Data
public class MyProp {
    private Integer userPropId;
    private String name;
    private String propImgUrl;
    private Integer type;
    private Integer userPetId;
    private List<AdditionalAttributes> additionalAttributes;
}
