package com.xye8.roc.service;

import com.xye8.roc.model.domain.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xye8.roc.model.request.UserRegisterRequest;

/**
* @author Xingzhi Ye
* @description 针对表【users】的数据库操作Service
* @createDate 2024-08-11 14:59:32
*/
public interface UsersService extends IService<Users> {

    long userRegister(UserRegisterRequest userRegisterRequest);
}
