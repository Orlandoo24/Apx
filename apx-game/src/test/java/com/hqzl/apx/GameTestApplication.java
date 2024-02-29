package com.hqzl.apx;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hqzl.apx.common.utils.JwtUtil;
import com.hqzl.apx.game.constant.GameConstants;
import com.hqzl.apx.game.enums.PetEnum;
import com.hqzl.apx.game.enums.PropEnum;
import com.hqzl.apx.game.pojo.AdditionalAttributes;
import com.hqzl.apx.game.pojo.MyPetInfoResp;
import com.hqzl.apx.game.pojo.MyProp;
import com.hqzl.apx.game.remote.AccountFeignRemote;
import com.hqzl.apx.mbg.mapper.UserPetMapper;
import com.hqzl.apx.mbg.mapper.UserPropMapper;
import com.hqzl.apx.mbg.model.UserPet;
import com.hqzl.apx.mbg.model.UserProp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameTestApplication {

    @Test
    public void test() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 2);
        map.put("expirationTime", System.currentTimeMillis() + 1000 * 60 * 60 * 24*24*24*24);
        String token = JwtUtil.generateToken(map);
        System.out.println(token);
    }



    @Test
    public void test2() {
        long l = System.currentTimeMillis();
        System.out.println(l);
        long nextTime = System.currentTimeMillis() + 30 * 60 * 1000 + 30 * 1000; // 假设nextTime是半小时30秒后的时间戳
        System.out.println(nextTime);

    }


    @Autowired
    private AccountFeignRemote accountFeignRemote;
    @Test
    public void test3() {
        MyPetInfoResp myPetInfoResp = new MyPetInfoResp();

        System.out.println(myPetInfoResp);

    }
}
