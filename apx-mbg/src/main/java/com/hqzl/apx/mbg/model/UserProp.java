package com.hqzl.apx.mbg.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class UserProp {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer propId;
    private Integer userPetId;
}
