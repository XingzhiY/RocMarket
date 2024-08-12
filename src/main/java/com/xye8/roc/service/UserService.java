package com.xye8.roc.service;

import com.xye8.roc.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xye8.roc.model.request.UserLoginRequest;
import com.xye8.roc.model.request.UserRegisterRequest;

import javax.servlet.http.HttpServletRequest;

/**
* @author Xingzhi Ye
* @description 针对表【users】的数据库操作Service
* @createDate 2024-08-11 14:59:32
*/
public interface UserService extends IService<User> {

    long userRegister(UserRegisterRequest userRegisterRequest);

    User userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request);

    int userLogout(HttpServletRequest request);

    User getCurrentUser(HttpServletRequest request);
}
