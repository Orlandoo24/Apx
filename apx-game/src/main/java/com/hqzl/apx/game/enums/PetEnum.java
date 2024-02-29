package com.hqzl.apx.game.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PetEnum {
    BEAR(1,"猴子","https://img1.baidu.com/it/u=3746969077,2283253897&fm=253&fmt=auto&app=138&f=JPEG?w=667&h=500",
           100 ,"30"),
    MONKEY(2, "鸡","https://img1.baidu.com/it/u=3332199712,943601215&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=666",100,"32"),
    CHICKEN(3,"熊","https://img2.baidu.com/it/u=630313551,2931951926&fm=253&fmt=auto&app=138&f=JPEG?w=550&h=412" ,100,"30");

    private Integer id;
    private String name;
    private String url;
    private Integer satietyMax;
    private String satietySpeed;

    public static PetEnum getPetAttributeEnum(int id) {
        for (PetEnum petEnum : PetEnum.values()) {
            if (petEnum.getId() == id) {
                return petEnum;
            }
        }
        return null;
    }
}
