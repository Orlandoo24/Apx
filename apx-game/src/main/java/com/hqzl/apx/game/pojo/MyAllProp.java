package com.hqzl.apx.game.pojo;

import com.hqzl.apx.game.pojo.MyProp;
import lombok.Data;

import java.util.List;

@Data
public class MyAllProp {
    private List<MyProp> consumables;
    private List<MyProp> equipments;
}