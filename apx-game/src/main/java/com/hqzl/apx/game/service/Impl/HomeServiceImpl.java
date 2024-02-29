package com.hqzl.apx.game.service.Impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hqzl.apx.common.config.SecurityContextHolder;
import com.hqzl.apx.common.constant.BlockchainConstants;
import com.hqzl.apx.common.constant.CommonConstants;
import com.hqzl.apx.common.domain.Result;
import com.hqzl.apx.common.enums.ResultCode;
import com.hqzl.apx.common.exception.ServiceException;
import com.hqzl.apx.game.constant.GameConstants;
import com.hqzl.apx.game.dao.HomeDao;
import com.hqzl.apx.game.enums.PetEnum;
import com.hqzl.apx.game.enums.PropEnum;
import com.hqzl.apx.game.pojo.*;
import com.hqzl.apx.game.remote.AccountFeignRemote;
import com.hqzl.apx.game.service.HomeService;
import com.hqzl.apx.game.service.ShopService;
import com.hqzl.apx.mbg.mapper.*;
import com.hqzl.apx.mbg.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    private ShopService shopService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserPropMapper userPropMapper;
    @Autowired
    private UserPetMapper userPetMapper;
//    @Autowired
//    private HomeDao homeDao;
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public String queryMyBalance() {
        // 获取用户余额
        User user = userMapper.selectById(Integer.valueOf(SecurityContextHolder.get(CommonConstants.APX_USER_KEY)));
        if (ObjectUtil.isEmpty(user)) {
            throw new ServiceException("用户不存在");
        }
        return user.getBalance();
    }

    @Override
    public List<MyPetInfoResp> queryMyPetInfo() {
        Integer userId = Integer.valueOf(SecurityContextHolder.get(CommonConstants.APX_USER_KEY));

        LambdaQueryWrapper<UserPet> petLambdaQueryWrapper = new LambdaQueryWrapper<>();
        petLambdaQueryWrapper.eq(UserPet::getUserId, userId);
        List<UserPet> userPets = userPetMapper.selectList(petLambdaQueryWrapper);

        List<MyPetInfoResp> myPetInfoResps = new ArrayList<>();

        for (UserPet userPet : userPets) {
            MyPetInfoResp myPetInfoResp = new MyPetInfoResp();
            PetEnum petAttributeEnum = PetEnum.getPetAttributeEnum(userPet.getId());
            myPetInfoResp.setUserPetId(userPet.getId());
            myPetInfoResp.setActive(userPet.getActive());
            myPetInfoResp.setIqValue(userPet.getIqValue());
            myPetInfoResp.setIqLevel(userPet.getIqLevel());
            myPetInfoResp.setLastTime(userPet.getLastTime());
            myPetInfoResp.setNextTime((userPet.getNextTime()-System.currentTimeMillis())/1000);

            myPetInfoResp.setSatietyValue(userPet.getSatietyValue());
            myPetInfoResp.setIqValueMax(GameConstants.IQ_Config_Map.get(userPet.getIqLevel()));
            myPetInfoResp.setPetImgUrl(petAttributeEnum.getUrl());
            myPetInfoResp.setPetName(petAttributeEnum.getName());

            // 基础必然有的
            myPetInfoResp.setSatietyMax(petAttributeEnum.getSatietyMax());
            myPetInfoResp.setLuckValue(userPet.getLuckValue());
            myPetInfoResp.setSatietySpeed(userPet.getSatietySpeed());

            LambdaQueryWrapper<UserProp> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(UserProp::getUserPetId, userPet.getId());
            List<UserProp> userProps = userPropMapper.selectList(lambdaQueryWrapper);
            List<MyProp> myProps = new ArrayList<>();

            for (UserProp userProp : userProps) {
                MyProp myProp = new MyProp();
                List<AdditionalAttributes> additionalAttributes = PropEnum.getAdditionalAttributes(userProp.getPropId());
                PropEnum propAttributeEnum = PropEnum.getPropAttributeEnum(userProp.getPropId());

                myProp.setAdditionalAttributes(additionalAttributes);
                myProp.setName(propAttributeEnum.getName());
                myProp.setUserPetId(userProp.getUserPetId());
                myProp.setUserPropId(userProp.getId());
                myProp.setPropImgUrl(propAttributeEnum.getImgUrl());
                myProp.setType(propAttributeEnum.getType());
                myProps.add(myProp);
                myPetInfoResp.setSatietyMax(myPetInfoResp.getSatietyMax() + propAttributeEnum.getSatietyMaxAdd());
                myPetInfoResp.setLuckValue(myPetInfoResp.getLuckValue() + propAttributeEnum.getLuckValueAdd());
                myPetInfoResp.setSatietySpeed(myPetInfoResp.getSatietySpeed() + propAttributeEnum.getSatietySpeedAdd());
            }
            myPetInfoResp.setMyProps(myProps);

            myPetInfoResps.add(myPetInfoResp);
        }


        return myPetInfoResps;
    }


    @Override
    public MyAllProp queryMyProp(Integer type) {
        List<UserProp> userProps = userPropMapper.selectList(null);

        MyAllProp myAllProp = new MyAllProp();
        List<MyProp> consumables = new ArrayList<>();
        List<MyProp> equipments = new ArrayList<>();

        for (UserProp userProp : userProps) {
            MyProp myProp = new MyProp();
            myProp.setUserPropId(userProp.getId());
            myProp.setUserPetId(userProp.getUserPetId());
            PropEnum propAttributeEnum = PropEnum.getPropAttributeEnum(userProp.getPropId());
            myProp.setName(propAttributeEnum.getName());
            myProp.setPropImgUrl(propAttributeEnum.getImgUrl());
            myProp.setType(propAttributeEnum.getType());
            List<AdditionalAttributes> additionalAttributes = PropEnum.getAdditionalAttributes(userProp.getPropId());
            myProp.setAdditionalAttributes(additionalAttributes);

            if (GameConstants.EQUIPMENT_TYPE.equals(myProp.getType())) {
                equipments.add(myProp);
            }
            if (GameConstants.CONSUMABLE_TYPE.equals(myProp.getType()) || GameConstants.BOX_TYPE.equals(myProp.getType())) {
                consumables.add(myProp);
            }
        }
        myAllProp.setConsumables(consumables);
        myAllProp.setEquipments(equipments);
        return myAllProp;
    }

    //
    @Override
    public void removeProp(Integer userPropId) {
        // 修改掉mysql 中的数据
        LambdaUpdateWrapper<UserProp> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(UserProp::getId, userPropId);
        lambdaUpdateWrapper.setSql("user_pet_id = null");
        userPropMapper.update(null, lambdaUpdateWrapper);
    }


    //
    @Override
    public void carryProp(Integer userPropId, Integer userPetId) {
        UserProp selectedUserProp = userPropMapper.selectById(userPropId);
        if (ObjectUtil.isEmpty(selectedUserProp)) {
            throw new ServiceException("用户没有该道具");
        }

        PropEnum selectedPropEnum = PropEnum.getPropAttributeEnum(selectedUserProp.getPropId());
        if (GameConstants.BOX_TYPE.equals(selectedPropEnum.getType())) {
            throw new ServiceException("宝箱不能被携带");
        }
        // 把原来这个道具的宠物绑定删除
        if (selectedUserProp.getUserPetId() != null) {
            removeProp(selectedUserProp.getId());
        }

        // 查询这个宠物所携带的装备
        LambdaQueryWrapper<UserProp> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserProp::getUserPetId, userPetId);
        List<UserProp> carriedUserProps = userPropMapper.selectList(lambdaQueryWrapper);
        // 可能有两种，循环，移除。如果类型一样删除原来有的


        for (UserProp carriedUserProp : carriedUserProps) {
            PropEnum carriedUserPropEnum = PropEnum.getPropAttributeEnum(carriedUserProp.getPropId());
            if (carriedUserPropEnum.getType().equals(selectedPropEnum.getType())) {
                removeProp(carriedUserProp.getId());
            }
        }
        // 绑定上
        selectedUserProp.setUserPetId(userPetId);
        userPropMapper.updateById(selectedUserProp);
    }

    @Override
    public ShopProp useProp(Integer userPropId, Integer userPetId) {
        UserProp userProp = userPropMapper.selectById(userPropId);
        UserPet userPet = userPetMapper.selectById(userPetId);

        if (ObjectUtil.isEmpty(userProp) || ObjectUtil.isEmpty(userPet)) {
            // 使用消耗品方法
            throw new ServiceException("没有这个宠物或装备");
        }

        PetEnum petEnum = PetEnum.getPetAttributeEnum(userPet.getPetId());
        PropEnum propEnum = PropEnum.getPropAttributeEnum(userProp.getPropId());

        if (GameConstants.EQUIPMENT_TYPE.equals(propEnum.getType()) || userProp.getUserPetId() != null) {
            throw new ServiceException("装备不能使用");
        }

        PropEnum usedPropEnum = null;
        if (GameConstants.CONSUMABLE_TYPE.equals(propEnum.getType())) {
            // 使用消耗品方法
            usedPropEnum = useConsumable(userPet, petEnum, propEnum);
        }

        if (GameConstants.BOX_TYPE.equals(propEnum.getType())) {
            // 走宝箱方法，开宝箱方法
            // 如果是消耗品再走一次useProp ，如果是装备，调用一次添加装备进入的sql语句
            usedPropEnum = useBox(userPet, petEnum);
        }

        ShopProp shopProp = new ShopProp();
        shopProp.setPropId(usedPropEnum.getId());
        shopProp.setPropImgUrl(usedPropEnum.getImgUrl());
        shopProp.setPrice(usedPropEnum.getPrice());
        shopProp.setName(usedPropEnum.getName());
        shopProp.setType(usedPropEnum.getType());
        shopProp.setAdditionalAttributes(PropEnum.getAdditionalAttributes(usedPropEnum.getId()));


        // 删除装备
        userPropMapper.deleteById(userProp.getId());
        return shopProp;
    }


    private PropEnum useConsumable(UserPet userPet, PetEnum petEnum, PropEnum propEnum) {
        LambdaQueryWrapper<UserProp> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserProp::getUserPetId, userPet.getId());
        List<UserProp> carriedUserProps = userPropMapper.selectList(lambdaQueryWrapper);

        Integer maxSatietyValue = petEnum.getSatietyMax();
        for (UserProp carriedUserProp : carriedUserProps) {
            PropEnum propAttributeEnum = PropEnum.getPropAttributeEnum(carriedUserProp.getPropId());
            maxSatietyValue += propAttributeEnum.getSatietyMaxAdd();
        }

        Integer usedConsumableSatietyValue = userPet.getSatietyValue() + propEnum.getSatietyValueBonus();
        userPet.setSatietyValue(usedConsumableSatietyValue > maxSatietyValue ? maxSatietyValue : usedConsumableSatietyValue);
        userPet.setIqValue(NumberUtil.add(propEnum.getIqValueBonus(), userPet.getIqValue()).intValue());
        userPet.setLuckValue(NumberUtil.add(propEnum.getLuckValueBonus(), userPet.getLuckValue()).intValue());
        userPetMapper.updateById(userPet);

        return propEnum;
    }

    private PropEnum useBox(UserPet userPet, PetEnum petEnum) {
        List<PropEnum> propEnums = Arrays.asList(PropEnum.values());
        List<PropEnum> filteredProps = propEnums.stream().filter(p -> !GameConstants.BOX_TYPE.equals(p.getType())).collect(Collectors.toList());
        PropEnum propEnum = RandomUtil.randomEle(filteredProps);

        if (GameConstants.CONSUMABLE_TYPE.equals(propEnum.getType())) {
            useConsumable(userPet, petEnum, propEnum);
        }
        if (GameConstants.EQUIPMENT_TYPE.equals(propEnum.getType())) {
            shopService.addUserProp(propEnum.getId(), userPet.getUserId());
        }
        return propEnum;
    }

    @Autowired
    private AccountFeignRemote accountFeignRemote;


    @Override
    public int upgradePet(Integer userPetId) {
        int res = 0;
        UserPet userPet = userPetMapper.selectById(userPetId);

        Integer upgradeIQValue = GameConstants.IQ_Config_Map.get(userPet.getIqLevel());
        if (userPet.getIqValue() < upgradeIQValue) {
            return res;
        }
        Result result = accountFeignRemote.withdrawBalance(BlockchainConstants.COMPANY_ADDRESS, GameConstants.UPGRADE_MONEY, BlockchainConstants.TOKEN_APXA);
        if (ResultCode.FAIL.getCode().equals(result.getCode())) {
            // 调用提现接口
            return res;
        }
        userPet.setIqLevel(userPet.getIqLevel() + 1);
        userPet.setIqValue(userPet.getIqValue() - upgradeIQValue);
        res = userPetMapper.updateById(userPet);
        return res;
    }
}
