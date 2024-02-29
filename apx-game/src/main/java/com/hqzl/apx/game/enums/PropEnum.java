package com.hqzl.apx.game.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hqzl.apx.game.pojo.AdditionalAttributes;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public enum PropEnum {

    RED_HAT(1,1,"小红帽","http://t14.baidu.com/it/u=204986681,771885082&fm=224&app=112&f=JPEG?w=350&h=350",
            "0.6",0, 0,0,0,
            "0.1",10,30,1),

    HAMBURG(2,2,"汉堡","https://img1.baidu.com/it/u=733557995,2184033731&fm=253&fmt=auto&app=138&f=JPEG?w=634&h=417",
            "0.3",   5, 0,80,0,
            "0",0,0,0),


    GLOVE(3,1,"手套","http://t13.baidu.com/it/u=4012085318,1090827073&fm=224&app=112&f=JPEG?w=500&h=500",
            "0.7",     0, 0,20,0,
            "0",1,10,0),

    FRENCH_FRIES(4,2,"薯条","https://img2.baidu.com/it/u=1294942428,2442499240&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=540",
            "0.2",    15, 1,20,0,
                     "0",0,0,0),

    BOX(100,3,"宝箱","https://img2.baidu.com/it/u=4020787438,2306740685&fm=253&fmt=auto&app=138&f=PNG?w=300&h=300",
                "0",0, 0,0,0,
                "0",0,0,0);

    private Integer id;
    private Integer type;
    private String name;
    private String imgUrl;
    private String price;

    // 消耗品才有
    private Integer iqValueBonus;
    private Integer luckValueBonus;
    private Integer satietyValueBonus;


    // 装备才有
    private Integer iqValueAdd;
    private String balanceAdd;
    private Integer luckValueAdd;
    private Integer satietyMaxAdd;
    private Integer satietySpeedAdd;


    public static PropEnum getPropAttributeEnum(int propId) {
        for (PropEnum propEnum : PropEnum.values()) {
            if (propEnum.getId() == propId) {
                return propEnum;
            }
        }
        return null;
    }


    private static final String ADDITIONAL_ATTRIBUTES_NAME_IQ = "IQ";
    private static final String ADDITIONAL_ATTRIBUTES_NAME_LUCKLY = "LUCKLY!";
    private static final String ADDITIONAL_ATTRIBUTES_NAME_SATIETY = "SATIETY!";
    public static List<AdditionalAttributes> getAdditionalAttributes(int propId) {
        PropEnum propEnum = getPropAttributeEnum(propId);
        List<AdditionalAttributes> additionalAttributes = new ArrayList<>();

        if (propEnum.getIqValueBonus() != 0) {
            AdditionalAttributes additionalAttributesPa = new AdditionalAttributes();
            additionalAttributesPa.setAdditionalAttributesName(PropEnum.ADDITIONAL_ATTRIBUTES_NAME_IQ);
            additionalAttributesPa.setAdditionalAttributesValue(propEnum.getIqValueBonus());
            additionalAttributes.add(additionalAttributesPa);
        }
        if (propEnum.getLuckValueBonus() != 0) {
            AdditionalAttributes additionalAttributesPa = new AdditionalAttributes();
            additionalAttributesPa.setAdditionalAttributesName(PropEnum.ADDITIONAL_ATTRIBUTES_NAME_LUCKLY);
            additionalAttributesPa.setAdditionalAttributesValue(propEnum.getLuckValueBonus());
            additionalAttributes.add(additionalAttributesPa);
        }
        if (propEnum.getSatietyValueBonus() != 0) {
            AdditionalAttributes additionalAttributesPa = new AdditionalAttributes();
            additionalAttributesPa.setAdditionalAttributesName(PropEnum.ADDITIONAL_ATTRIBUTES_NAME_SATIETY);
            additionalAttributesPa.setAdditionalAttributesValue(propEnum.getSatietyValueBonus());
            additionalAttributes.add(additionalAttributesPa);
        }
        if (propEnum.getIqValueAdd() != 0) {
            AdditionalAttributes additionalAttributesPa = new AdditionalAttributes();
            additionalAttributesPa.setAdditionalAttributesName("智商值额外获取");
            additionalAttributesPa.setAdditionalAttributesValue(propEnum.getIqValueAdd());
            additionalAttributes.add(additionalAttributesPa);
        }
        if (!propEnum.getBalanceAdd().equals("0")) {
            AdditionalAttributes additionalAttributesPa = new AdditionalAttributes();
            additionalAttributesPa.setAdditionalAttributesName("金币额外获取");
            additionalAttributesPa.setAdditionalAttributesValue(propEnum.getBalanceAdd());
            additionalAttributes.add(additionalAttributesPa);
        }
        if (propEnum.getLuckValueAdd() != 0) {
            AdditionalAttributes additionalAttributesPa = new AdditionalAttributes();
            additionalAttributesPa.setAdditionalAttributesName("幸运值额外增加");
            additionalAttributesPa.setAdditionalAttributesValue(propEnum.getLuckValueAdd());
            additionalAttributes.add(additionalAttributesPa);
        }
        if (propEnum.getSatietySpeedAdd().equals("0")) {
            AdditionalAttributes additionalAttributesPa = new AdditionalAttributes();
            additionalAttributesPa.setAdditionalAttributesName("持久力提升");
            additionalAttributesPa.setAdditionalAttributesValue(propEnum.getLuckValueAdd());
            additionalAttributes.add(additionalAttributesPa);
        }
        if (propEnum.getSatietyMaxAdd() != 0) {
            AdditionalAttributes additionalAttributesPa = new AdditionalAttributes();
            additionalAttributesPa.setAdditionalAttributesName("饱腹值上限");
            additionalAttributesPa.setAdditionalAttributesValue(propEnum.getLuckValueAdd());
            additionalAttributes.add(additionalAttributesPa);
        }
        return additionalAttributes;
    }

}
