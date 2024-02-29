package com.hqzl.apx.game.service;


import com.hqzl.apx.game.pojo.ShopProp;
import com.hqzl.apx.mbg.model.Prop;

import java.util.List;

public interface ShopService {
    int buyProp(Integer propId);
    int addUserProp(Integer propId, Integer userId);

    List<ShopProp> queryProp();
}
