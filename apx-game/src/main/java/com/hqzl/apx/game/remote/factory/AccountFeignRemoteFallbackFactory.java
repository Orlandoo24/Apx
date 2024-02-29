package com.hqzl.apx.game.remote.factory;

import com.hqzl.apx.common.domain.Result;
import com.hqzl.apx.common.exception.ServiceException;
import com.hqzl.apx.game.remote.AccountFeignRemote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author ruoyi
 */
@Slf4j
@Component
public class AccountFeignRemoteFallbackFactory implements FallbackFactory<AccountFeignRemote> {

    @Override
    public AccountFeignRemote create(Throwable cause) {
        log.error("远程服务调用失败:{}", cause.getMessage());
        return new AccountFeignRemote() {
            @Override
            public Result withdrawBalance(String toAddress, String amount, String token) {
                return Result.failed();
            }
        };
    }
}
