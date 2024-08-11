package com.xye8.roc.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * http接收的用户注册请求体json
 *

 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String userEmail;
    private String userName;

    private String userPassword;

    private String checkPassword;

}
