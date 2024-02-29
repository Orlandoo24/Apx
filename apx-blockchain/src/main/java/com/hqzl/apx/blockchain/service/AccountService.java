package com.hqzl.apx.blockchain.service;


import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface AccountService {
//    int newAccount(NewAccountReq newAccountReq);

    Map<String, String> exportAccount();

    Map<String, String> queryBalance(Integer userId);

    int withdrawBalance(String toAddress, String amount, String token);

    @Transactional(rollbackFor = Exception.class)
    int claimBalance();

}
