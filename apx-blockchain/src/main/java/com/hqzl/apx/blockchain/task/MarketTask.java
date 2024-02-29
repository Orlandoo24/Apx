package com.hqzl.apx.blockchain.task;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hqzl.apx.blockchain.pojo.LeafProof;
import com.hqzl.apx.blockchain.pojo.MerkelTree;
import com.hqzl.apx.common.constant.BlockchainConstants;
import com.hqzl.apx.common.constant.CommonConstants;
import com.hqzl.apx.common.constant.RedisConstants;
import com.hqzl.apx.mbg.mapper.UserMapper;
import com.hqzl.apx.mbg.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Log4j2
public class MarketTask {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Scheduled(cron = "30 30 * * * ?")
    private void createMarket() {
        Set<String> keys = redisTemplate.keys(RedisConstants.USER_ADDRESS + "*");
        redisTemplate.delete(keys);
        redisTemplate.delete(RedisConstants.MERKEL_TREE_ROOT);

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.ne(User::getBalance, "0");
        List<User> users = userMapper.selectList(lambdaQueryWrapper);
        if (ObjectUtil.isEmpty(users)) {
            return;
        }
        List<Map<String, String>> list = new ArrayList();
        for (User user : users) {
            HashMap<String, String> map = new HashMap<>();
            map.put("address", user.getAddress());
            map.put("amount", String.format("%.0f", NumberUtil.mul(Double.valueOf(user.getBalance()).doubleValue(), Math.pow(10, 18))));
            list.add(map);
        }
        Map<String, List> param = new HashMap<>();
        param.put("claimsInfo", list);
        String resp = HttpUtil.post(CommonConstants.HTTP + BlockchainConstants.ON_CHAIN_SERVER_IP + "/createClaimMerkleTree", JSONUtil.toJsonStr(param));

        MerkelTree merkelTree = JSONUtil.toBean(resp, MerkelTree.class);
        redisTemplate.opsForValue().set(RedisConstants.MERKEL_TREE_ROOT, merkelTree.getRoot());
        List<LeafProof> leafProofs = merkelTree.getLeafProof();
        for (LeafProof leafProof : leafProofs) {
            redisTemplate.opsForValue().set(RedisConstants.USER_ADDRESS + leafProof.getValue().get(0), leafProof);
        }
        log.info("在{}生成Market", new Date());
    }
}
