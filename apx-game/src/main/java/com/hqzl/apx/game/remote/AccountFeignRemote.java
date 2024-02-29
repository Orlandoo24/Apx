package com.hqzl.apx.game.remote;


import com.hqzl.apx.common.domain.Result;
import com.hqzl.apx.game.remote.factory.AccountFeignRemoteFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Component
@FeignClient(value = "apx-blockchain",fallback = AccountFeignRemoteFallbackFactory.class)
public interface AccountFeignRemote {

    @GetMapping ("account/balance/withdraw")
    Result withdrawBalance(@RequestParam("toAddress") String toAddress,@RequestParam("amount") String amount,@RequestParam("token") String token);
}
