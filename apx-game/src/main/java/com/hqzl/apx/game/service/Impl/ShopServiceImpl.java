package com.hqzl.apx.game.service.Impl;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hqzl.apx.common.config.SecurityContextHolder;
import com.hqzl.apx.common.constant.BlockchainConstants;
import com.hqzl.apx.common.constant.CommonConstants;
import com.hqzl.apx.common.constant.RedisConstants;
import com.hqzl.apx.common.domain.Result;
import com.hqzl.apx.common.enums.ResultCode;
import com.hqzl.apx.game.constant.GameConstants;
import com.hqzl.apx.game.enums.PropEnum;
import com.hqzl.apx.game.pojo.ShopProp;
import com.hqzl.apx.game.remote.AccountFeignRemote;
import com.hqzl.apx.game.service.ShopService;
import com.hqzl.apx.mbg.mapper.PropMapper;
import com.hqzl.apx.mbg.mapper.UserPropMapper;
import com.hqzl.apx.mbg.model.Prop;
import com.hqzl.apx.mbg.model.UserProp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopServiceImpl implements ShopService {


    @Autowired
    private UserPropMapper userPropMapper;

    @Autowired
    private PropMapper propMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private AccountFeignRemote accountFeignService;

    @Override
    public int buyProp(Integer propId) {
        Integer userId = Integer.valueOf(SecurityContextHolder.get(CommonConstants.APX_USER_KEY));
        // 远程调用提现接口
        Result result = accountFeignService.withdrawBalance(BlockchainConstants.COMPANY_ADDRESS, PropEnum.getPropAttributeEnum(propId).getPrice(), BlockchainConstants.TOKEN_APXA);
        if (ResultCode.FAIL.getCode().equals(result.getCode())) {
            return 0;
        }
        return addUserProp(userId, propId);
    }

    public int addUserProp(Integer propId, Integer userId) {
        UserProp userProp = new UserProp();
        userProp.setUserId(userId);
        userProp.setPropId(propId);
        return userPropMapper.insert(userProp);
    }

    @Override
    public List<ShopProp> queryProp() {
        List<ShopProp> shopProps = new ArrayList<>();
        List<PropEnum> propEnums = Arrays.asList(PropEnum.values());
        List<PropEnum> filteredProps = propEnums.stream().filter(p -> !GameConstants.BOX_TYPE.equals(p.getType())).collect(Collectors.toList());
        for (PropEnum filteredProp : filteredProps) {
            ShopProp shopProp = new ShopProp();
            shopProp.setPropId(filteredProp.getId());
            shopProp.setPropImgUrl(filteredProp.getImgUrl());
            shopProp.setName(filteredProp.getName());
            shopProp.setType(filteredProp.getType());
            shopProp.setPrice(filteredProp.getPrice());
            shopProp.setAdditionalAttributes(PropEnum.getAdditionalAttributes(filteredProp.getId()));
            shopProps.add(shopProp);
        }
        return shopProps;
    }
}
