package com.hqzl.apx.blockchain.service;

public interface TwoFactorService {

    /**
     * 获取 2faSecretKey 秘钥，一个用户只能获取一个
     * @return
     */
    String get2faSecretKey();
}
