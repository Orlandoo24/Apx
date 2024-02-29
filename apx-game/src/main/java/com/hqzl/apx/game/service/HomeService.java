package com.hqzl.apx.game.service;
import com.hqzl.apx.game.enums.PropEnum;
import com.hqzl.apx.game.pojo.MyAllProp;
import com.hqzl.apx.game.pojo.MyPetInfoResp;
import com.hqzl.apx.game.pojo.ShopProp;
import com.hqzl.apx.mbg.model.Prop;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HomeService {
    /**
     * 查询该用户余额
     * 1、直接查询user表返回余额字段
     * @return
     */
    String queryMyBalance();

    /**
     * 查询该用户背包，类型两种
     * @param type type 等于null时，返回所有道具；等于1时，返回装备道具；等于2时返回消耗品道具；
     * @return
     */
    MyAllProp queryMyProp(Integer type);


    /**
     * 返回该用户所有宠物信息（属性【经过计算处理后的】、装备）
     * @return
     */
    List<MyPetInfoResp> queryMyPetInfo();

    @Transactional(rollbackFor = Exception.class)
    void removeProp(Integer userPropId);
//
    @Transactional(rollbackFor = Exception.class)
    void carryProp(Integer userPropId, Integer userPetId);
//
    /**
     * 喂食
     * 1、先判断道具是否是消耗品
     * 2、判断该道具是否足够（携带数必须小于数量）
     * 3、判断是否已经达到每日上限
     * 4、吃，增加宠物属性，判断是否升级（单独写一个增加消耗品属性的方法）
     * @param userPropId
     * @param userPetId
     */
    @Transactional(rollbackFor = Exception.class)
    ShopProp useProp(Integer userPropId, Integer userPetId);


    @Transactional(rollbackFor = Exception.class)
    int upgradePet(Integer userPetId);

}
