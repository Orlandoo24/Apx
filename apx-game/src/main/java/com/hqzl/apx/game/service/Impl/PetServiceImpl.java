package com.hqzl.apx.game.service.Impl;

import com.hqzl.apx.game.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class PetServiceImpl implements PetService {


    @Autowired
    private RedisTemplate<String,String> redisTemplate;

//    @Autowired
//    private ConfigMapper configMapper;

    @Override
    public String queryAllPetDetails() {
        return null;
    }
}
