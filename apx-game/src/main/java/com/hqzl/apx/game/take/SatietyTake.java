package com.hqzl.apx.game.take;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hqzl.apx.game.enums.PropEnum;
import com.hqzl.apx.game.service.ConnectService;
import com.hqzl.apx.mbg.mapper.UserPetMapper;
import com.hqzl.apx.mbg.mapper.UserPropMapper;
import com.hqzl.apx.mbg.model.UserPet;
import com.hqzl.apx.mbg.model.UserProp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SatietyTake {

    @Autowired
    private UserPetMapper userPetMapper;

    @Autowired
    private UserPropMapper userPropMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    // 激活的宠物要扣到0 就不能扣了 ， 未激活的宠物要扣到1 就不能扣了，当不能再扣的时候将饱腹值状态存入数据库中
    // 多久消耗一次    宠物       这个宠物的原本消耗速度，装备
    // 体力值
    // 上一次消耗的时间 - 现在时间 = 已经过的时间
    // 已经过的时间 - 多久消耗一次的时间
    // 大于0 更新， 小于0不更新
    // 现在的时间+多久消耗一次 = 下一次要更新的时间


    // 上次要更新的时间  下一次要更新的时间
    // 现在时间 - 下一次要更新的时间 = 大于0 更新， 小于0不更新
    // 下一次要更新的时间 +  多久消耗一次的时间 = 下一次要更新的时间
    // 上次要更新的时间 = 现在时间
    @Scheduled(cron = "0 */1 * * * ?")
    private void subSatietyValue() {
        List<UserPet> userPets = userPetMapper.selectList(null);

        for (UserPet userPet : userPets) {
            if ((userPet.getActive() == 1 && userPet.getSatietyValue() == 0)
                    || (userPet.getActive() == 0 && userPet.getSatietyValue() == 1)) {
                continue;
            }
            if (System.currentTimeMillis() - userPet.getNextTime() > 0) {
                int satietyValueSpeed = userPet.getSatietySpeed() + userPet.getIqLevel();
                LambdaQueryWrapper<UserProp> lambdaQueryWrapper = new LambdaQueryWrapper();
                lambdaQueryWrapper.eq(UserProp::getUserPetId, userPet.getId());
                List<UserProp> userProps = userPropMapper.selectList(lambdaQueryWrapper);
                for (UserProp userProp : userProps) {
                    PropEnum propAttributeEnum = PropEnum.getPropAttributeEnum(userProp.getPropId());
                    satietyValueSpeed += propAttributeEnum.getSatietySpeedAdd();
                }
                userPet.setSatietyValue(userPet.getSatietyValue() - 1);
                userPet.setNextTime(DateUtil.offsetMinute(DateUtil.date(), satietyValueSpeed).toTimestamp().getTime() + userPet.getNextTime());
                userPet.setLastTime(userPet.getNextTime());
                userPetMapper.updateById(userPet);
            }

        }
    }


    @Autowired
    private ConnectService connectService;


}
