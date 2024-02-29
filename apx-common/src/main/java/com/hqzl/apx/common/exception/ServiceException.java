package com.hqzl.apx.common.exception;

import com.hqzl.apx.common.enums.ResultCode;
import lombok.Data;

/**
 * 自定义API异常
 *
 * by lintianhong
 */

@Data
public final class ServiceException extends RuntimeException{

    private String message;

    public ServiceException(String message) {
        this.message = message;
    }
}
