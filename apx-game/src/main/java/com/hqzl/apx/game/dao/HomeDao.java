package com.hqzl.apx.game.dao;

import com.hqzl.apx.game.pojo.MyProp;
import com.hqzl.apx.game.pojo.MyPetInfoResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HomeDao {


    List<MyProp> selectMyProp(@Param("userId") Integer userId
            , @Param("type") Integer type);



    /**
     *
     * @param userPetId   查询我的宠物信息
     * @return
     */
    List<MyPetInfoResp> selectMyPetInfo(@Param("userId") Integer userPetId);
}
