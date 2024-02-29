package com.hqzl.apx.game.service.Impl;

import com.hqzl.apx.game.service.ConnectService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ConnectServiceImpl implements ConnectService {
    private Map<String,String> userMap = new ConcurrentHashMap<>();


    @Override
    public void addChannel(String userId, String channel) {
        userMap.put(userId,channel);
    }

    @Override
    public void removeChannel(String userId) {
        userMap.remove(userId);
    }

    @Override
    public String getChannel(String userId) {
        return userMap.get(userId);
    }
}
