package com.hqzl.apx.common.domain;


import com.hqzl.apx.common.enums.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class Result<T>{

    private Integer code;
    private String message;
    private T data;

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success(T data){
        return new Result<T>(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMessage(),data);
    }

    public static <T> Result<T> failed(String message){
        return new Result<T>(ResultCode.FAIL.getCode(), message,null);
    }

    public static <T> Result<T> failed(int code, String message){
        return new Result<T>(code, message,null);
    }
    public static <T> Result<T> failed(){
        return new Result<T>(ResultCode.FAIL.getCode(), ResultCode.FAIL.getMessage(),null);
    }
}
