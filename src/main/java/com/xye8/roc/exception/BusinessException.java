package com.xye8.roc.exception;

import com.xye8.roc.common.ErrorCode;
import lombok.Data;

/**
 * 自定义异常类
 *
 */

public class BusinessException extends RuntimeException {


    private final ErrorCode errorCode;

    private final String description;


    public BusinessException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.description = "有没 description";
    }

    public BusinessException(ErrorCode errorCode, String description) {
        this.errorCode = errorCode;
        this.description = description;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getDescription() {
        return description;
    }
}
