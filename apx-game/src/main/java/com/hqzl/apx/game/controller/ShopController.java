package com.hqzl.apx.game.controller;


import com.hqzl.apx.common.domain.Result;
import com.hqzl.apx.game.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("shop")
public class ShopController {


    @Autowired
    private ShopService shopService;

    @GetMapping("prop/query")
    public Result queryProp() {
        return Result.success(shopService.queryProp());
    }

    @GetMapping("prop/buy")
    public Result buyProp(@RequestParam("propId") Integer propId) {
        if (shopService.buyProp(propId) > 0) {
            return Result.success(null);
        }
        return Result.failed();
    }
}
