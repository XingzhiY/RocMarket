package com.xye8.roc.service;

import com.xye8.roc.model.domain.RocUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xye8.roc.model.dto.UserRegisterDto;
import com.xye8.roc.model.request.UserLoginRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author Xingzhi Ye
* @description 针对表【roc_user(用户信息表)】的数据库操作Service
* @createDate 2024-08-07 21:07:43
*/
public interface RocUserService extends IService<RocUser> {
    /**
     *
     * @param userRegisterDto
     * @return id
     */
    long userRegister(UserRegisterDto userRegisterDto);

    RocUser userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request);

//    /**
//     * 用户脱敏
//     *
//     * @param originUser
//     * @return
//     */
//    User getSafetyUser(User originUser);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);

//    /**
//     * 根据标签搜索用户
//     *
//     * @param tagNameList
//     * @return
//     */
//    List<RocUser> searchUsersByTags(List<String> tagList);

//    /**
//     * 更新用户信息
//     * @param user
//     * @return
//     */
//    int updateUser(User user, User loginUser);
//
//    /**
//     * 获取当前登录用户信息
//     * @return
//     */
//    User getLoginUser(HttpServletRequest request);
//
//    /**
//     * 是否为管理员
//     *
//     * @param request
//     * @return
//     */
//    boolean isAdmin(HttpServletRequest request);
//
//    /**
//     * 是否为管理员
//     *
//     * @param loginUser
//     * @return
//     */
//    boolean isAdmin(User loginUser);
//
//    /**
//     * 匹配用户
//     * @param num
//     * @param loginUser
//     * @return
//     */
//    List<User> matchUsers(long num, User loginUser);
}
