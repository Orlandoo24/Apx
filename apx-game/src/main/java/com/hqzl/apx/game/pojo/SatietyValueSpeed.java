package com.hqzl.apx.game.pojo;


import lombok.Data;

@Data
public class SatietyValueSpeed {
    private Integer userPetId;
    private Integer active;
    private Integer satietyValue;
    private Integer satietySpeed;
    private Long updateIntervalSeconds;
}
