package com.xye8.roc.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 *
 * @param <T>
 */
@Data
public class BaseResponse<T> implements Serializable {

    private int code;

    private T data;

    private String message;

    private String description;

    public BaseResponse(int code, String message, String description, T data) {
        this.code = code;
        this.message = message;
        this.description = description;
        this.data = data;
    }

//
//    public BaseResponse(int code, T data, String message) {
//        this(code, data, message, "");
//    }
//
//    public BaseResponse(int code, T data) {
//        this(code, data, "", "");
//    }
//
//    public BaseResponse(ErrorCode errorCode) {
//        this(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());
//    }
}
