package com.hqzl.apx.game.service;

public interface ConnectService {

    void addChannel(String userId,String channel);
    void removeChannel(String userId);
    String getChannel(String userId);

}
