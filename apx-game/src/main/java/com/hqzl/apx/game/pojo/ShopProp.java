package com.hqzl.apx.game.pojo;

import lombok.Data;

import java.util.List;

@Data
public class ShopProp {
    private Integer propId;
    private String name;
    private String propImgUrl;
    private Integer type;
    private String price;
    private List<AdditionalAttributes> additionalAttributes;
}
