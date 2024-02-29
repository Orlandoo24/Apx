package com.hqzl.apx.game.constant;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用常量信息
 *
 * @author ruoyi
 */
public class GameConstants {
    public final static Integer EQUIPMENT_TYPE = 1;
    public final static Integer CONSUMABLE_TYPE = 2;
    public final static Integer BOX_TYPE = 3;
    public final static String UPGRADE_MONEY = "0.1"; //100
    private final static Integer LUCK_LOWER_LIMIT = -999;
    private final static Integer LUCK_UPPER_LIMIT = -950;
    private final static Integer IQ_LOWER_LIMIT = 1;
    private final static Integer IQ_UPPER_LIMIT = 10;
    private final static Integer MINING_LOWER_LIMIT = 10;
    private final static Integer MINING_UPPER_LIMIT = 15;
    public final static Map<Integer, Integer> IQ_Config_Map = getImmutableMap();






    private static Map<Integer, Integer> getImmutableMap() {
        HashMap<Integer, Integer> iqConfigMap = new HashMap<>();
        iqConfigMap.put(1, 20);
        iqConfigMap.put(2, 10);
        iqConfigMap.put(3, 10);
        iqConfigMap.put(4, 10);
        iqConfigMap.put(5, 10);
        iqConfigMap.put(6, 10);
        iqConfigMap.put(7, 10);
        iqConfigMap.put(8, 10);
        iqConfigMap.put(9, 10);
        iqConfigMap.put(10, 10);
        iqConfigMap.put(11, 20);
        iqConfigMap.put(12, 20);
        iqConfigMap.put(13, 20);
        iqConfigMap.put(14, 20);
        iqConfigMap.put(15, 20);
        iqConfigMap.put(16, 20);
        iqConfigMap.put(17, 20);
        iqConfigMap.put(18, 20);
        iqConfigMap.put(19, 20);
        iqConfigMap.put(20, 20);
        iqConfigMap.put(21, 20);
        iqConfigMap.put(22, 20);
        iqConfigMap.put(23, 20);
        iqConfigMap.put(24, 20);
        iqConfigMap.put(25, 20);
        iqConfigMap.put(26, 20);
        iqConfigMap.put(27, 20);
        iqConfigMap.put(28, 20);
        iqConfigMap.put(29, 20);
        iqConfigMap.put(30, 20);
        return Collections.unmodifiableMap(iqConfigMap);
    }
}
