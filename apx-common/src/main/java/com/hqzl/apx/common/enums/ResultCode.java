package com.hqzl.apx.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultCode {
    SUCCESS(100, "操作成功"),
    FAIL(101, "操作失败"),
    UNAUTHORIZED(104, "暂未登录或token已经过期"),
    SERVLET_ERROR(105, "servlet 错误"),
    SERVER_ERROR(110, "接口异常");

    private Integer code;
    private String message;
}
