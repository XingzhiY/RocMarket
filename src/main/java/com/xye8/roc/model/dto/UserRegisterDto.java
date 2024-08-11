package com.xye8.roc.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterDto implements Serializable {
    private static final long serialVersionUID = 3191241716373120793L;

    private String userEmail;

    private String userPassword;

    private String checkPassword;
}
