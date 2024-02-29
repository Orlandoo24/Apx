package com.hqzl.apx.blockchain.service.Impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.hqzl.apx.blockchain.pojo.LeafProof;
import com.hqzl.apx.blockchain.service.AccountService;
import com.hqzl.apx.common.config.SecurityContextHolder;
import com.hqzl.apx.common.constant.BlockchainConstants;
import com.hqzl.apx.common.constant.CommonConstants;
import com.hqzl.apx.common.constant.RedisConstants;
import com.hqzl.apx.common.exception.ServiceException;
import com.hqzl.apx.mbg.mapper.UserMapper;
import com.hqzl.apx.mbg.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

//    @Override
//    public int newAccount(NewAccountReq newAccountReq) {
//        // 未写完
//        User user = userMapper.selectById(newAccountReq.getUserId());
//
//        if (ObjectUtil.isEmpty(user)){
//            throw new ApxServiceException("用户不存在");
//        }
//        if (StrUtil.isNotBlank(user.getAddress())|| StrUtil.isBlank(user.getEncryptedPrivateKey())){
//            throw new ApxServiceException("已经创建过账户");
//        }
//
//        HashMap<String, String> param = new HashMap();
//        param.put("userId",user.getTpId());
//        String resp = (String) restTemplateService.post( CommonConstants.ON_CHAIN_SERVER_IP+"/newAccount", String.class, param);
//        Map<String, String> map = JSONUtil.toBean(resp, Map.class);
//
//        String address = map.get("address");
//        String encryptedPrivateKey = map.get("encryptedPrivateKey");
//
//        if (StrUtil.isNotBlank(address)|| StrUtil.isBlank(encryptedPrivateKey)){
//            throw new ApxServiceException("链上服务器请求失败");
//        }
//        user.setAddress(address);
//        user.setEncryptedPrivateKey(encryptedPrivateKey);
//
//        System.out.println(resp);
//        return userMapper.updateById(user);
//    }


    @Override
    public Map<String, String> exportAccount() {
        User user = userMapper.selectById(1);
        if (ObjectUtil.isEmpty(user)) {
            throw new ServiceException("用户不存在");
        }
        HashMap<String, String> param = new HashMap();
        param.put("userId", user.getTpId());
        param.put("encryptedPrivateKey", user.getEncryptedPrivateKey());

        String resp = HttpUtil.post(BlockchainConstants.ON_CHAIN_SERVER_IP + "/exportAccount", JSONUtil.toJsonStr(param));
        Map<String, String> map = JSONUtil.toBean(resp, Map.class);
        return map;
    }

    @Override
    public Map<String, String> queryBalance(Integer userId) {
//        Integer userId = Integer.valueOf(SecurityContextHolder.get(CommonConstants.APX_USER_KEY));
        User user = userMapper.selectById(userId);
        if (ObjectUtil.isEmpty(user)) {
            throw new ServiceException("用户不存在");
        }
        String resp = HttpUtil.get(BlockchainConstants.ON_CHAIN_SERVER_IP + "/balance?address=" + user.getAddress());
        Map<String, String> map = JSONUtil.toBean(resp, Map.class);
        return map;
    }

    @Override
    public int withdrawBalance(String toAddress, String amount, String token) {
        User user = userMapper.selectById(Integer.valueOf(SecurityContextHolder.get(CommonConstants.APX_USER_KEY)));
        HashMap<String, String> param = new HashMap();
        param.put("userId", user.getId() + "");
        param.put("fromAddress", user.getAddress());
        param.put("encryptedPrivateKey", user.getEncryptedPrivateKey());
        param.put("toAddress", toAddress);
        param.put("amount", amount);
        param.put("token", token);

        String resp = HttpUtil.post(CommonConstants.HTTP + BlockchainConstants.ON_CHAIN_SERVER_IP + "/withdraw", JSONUtil.toJsonStr(param));
        Map<String, String> map = JSONUtil.toBean(resp, Map.class);
        if (CollectionUtil.isEmpty(map) || StrUtil.isBlank(map.get("txHash"))) {
            return 0;
        }
        return 1;
    }

    @Override
    public int claimBalance() {
        int res = 0;
        Integer userId = Integer.valueOf(SecurityContextHolder.get(CommonConstants.APX_USER_KEY));
        User user = userMapper.selectById(userId);
        if (ObjectUtil.isEmpty(user)) {
            throw new ServiceException("用户不存在");
        }
        String redisKeyAddress = RedisConstants.USER_ADDRESS + user.getAddress();
        LeafProof leafProof = (LeafProof) redisTemplate.opsForValue().get(redisKeyAddress);
        String root = (String) redisTemplate.opsForValue().get(RedisConstants.MERKEL_TREE_ROOT);
        if (ObjectUtil.isEmpty(leafProof)) {
            return res;
        }

        String balanceInMerkelTree = leafProof.getValue().get(1);
        HashMap<String, Object> param = new HashMap();
        param.put("amount", balanceInMerkelTree);
        param.put("encryptedPrivateKey", user.getEncryptedPrivateKey());
        param.put("userId", user.getId() + "");
        param.put("fromAddress", user.getAddress());
        param.put("proof", leafProof.getProof());
        param.put("root", root);

        String resp = HttpUtil.post(CommonConstants.HTTP + BlockchainConstants.ON_CHAIN_SERVER_IP + "/claim", JSONUtil.toJsonStr(param));
        Map<String, String> map = JSONUtil.toBean(resp, Map.class);
        if (CollectionUtil.isEmpty(map) || StrUtil.isBlank(map.get("txHash"))) {
            throw new ServiceException("提取失败");
        }

        user.setBalance(NumberUtil.sub(user.getBalance(), NumberUtil.div(new BigDecimal(balanceInMerkelTree), new BigDecimal("1E18")).toPlainString()).toPlainString());
        redisTemplate.delete(redisKeyAddress);
        res = userMapper.updateById(user);

        return res;
    }
}


