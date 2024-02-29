package com.hqzl.apx.blockchain.controller;

import com.hqzl.apx.blockchain.service.AccountService;
import com.hqzl.apx.common.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("account")
@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("account/export")
    public Result exportAccount() {
        return Result.success(accountService.exportAccount());
    }

    @GetMapping("balance/query")
    public Result queryWalletBalance(@RequestParam("userId") Integer userId) {
        return Result.success(accountService.queryBalance(userId));
    }

    @GetMapping("balance/withdraw")
    public Result withdrawBalance(@RequestParam("toAddress") String toAddress, @RequestParam("amount") String amount, @RequestParam("token") String token) {
        if (accountService.withdrawBalance(toAddress, amount, token) > 0) {
            return Result.success(null);
        }
        return Result.failed();
    }

    // developed
    @GetMapping("balance/claim")
    public Result claimBalance() {
        if (accountService.claimBalance() > 0) {
            return Result.success(null);
        }
        return Result.failed();
    }
}
