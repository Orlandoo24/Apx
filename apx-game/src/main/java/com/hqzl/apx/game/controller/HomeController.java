package com.hqzl.apx.game.controller;

import com.hqzl.apx.common.domain.Result;
import com.hqzl.apx.game.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    // tested
    @GetMapping("balance/query")
    public Result queryMyBalance() {
        return Result.success(homeService.queryMyBalance());
    }

    // tested
    @GetMapping("petInfo/query")
    public Result queryMyPetPropInfo() {
        return Result.success(homeService.queryMyPetInfo());
    }


    // tested
    @GetMapping("prop/query")
    public Result queryMyProp(@RequestParam(value = "type", required = false) Integer type) {
        return Result.success(homeService.queryMyProp(type));
    }

    // developed
    @GetMapping("prop/use")
    public Result useProp(@RequestParam("userPropId") Integer userPropId, @RequestParam("userPetId") Integer userPetId) {
        return Result.success(homeService.useProp(userPropId, userPetId));
    }


    // tested
    @GetMapping("prop/remove")
    public Result removeProp(@RequestParam("userPropId") Integer userPropId) {
        homeService.removeProp(userPropId);
        return Result.success(null);
    }

    // tested
    @GetMapping("prop/carry")
    public Result carryProp(@RequestParam("userPropId") Integer userPropId, @RequestParam("userPetId") Integer userPetId) {
        homeService.carryProp(userPropId, userPetId);
        return Result.success(null);
    }

    @GetMapping("pet/upgrade")
    public Result upgradePet(@RequestParam("userPetId") Integer userPetId) {
        if (homeService.upgradePet(userPetId) > 0) {
            return Result.success(null);
        }
        return Result.failed();
    }
}
