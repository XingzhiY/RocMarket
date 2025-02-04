package com.xye8.roc.common;

/**
 * 返回工具类
 *
 */
public class ResultUtils {

    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(),ErrorCode.SUCCESS.getMessage(),"成功",data);
    }

    /**
     * 失败
     *
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode.getCode(), errorCode.getMessage(), "没有详细描述",null);
    }    /**
     * 失败
     *
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode,String description) {
        return new BaseResponse<>(errorCode.getCode(), errorCode.getMessage(), description,null);
    }

//
//    /**
//     * 失败
//     *
//     * @param code
//     * @param message
//     * @param description
//     * @return
//     */
//    public static BaseResponse error(int code, String message, String description) {
//        return new BaseResponse(code, null, message, description);
//    }
//
//    /**
//     * 失败
//     *
//     * @param errorCode
//     * @return
//     */
//    public static BaseResponse error(ErrorCode errorCode, String message, String description) {
//        return new BaseResponse(errorCode.getCode(), null, message, description);
//    }
//
//    /**
//     * 失败
//     *
//     * @param errorCode
//     * @return
//     */
//    public static BaseResponse error(ErrorCode errorCode, String description) {
//        return new BaseResponse(errorCode.getCode(), errorCode.getMessage(), description);
//    }
}
