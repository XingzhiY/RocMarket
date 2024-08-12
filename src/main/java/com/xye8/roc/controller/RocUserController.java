package com.xye8.roc.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xye8.roc.common.BaseResponse;
import com.xye8.roc.common.ErrorCode;
import com.xye8.roc.common.ResultUtils;
import com.xye8.roc.exception.BusinessException;
import com.xye8.roc.model.domain.RocUser;
import com.xye8.roc.model.dto.UserRegisterDto;
import com.xye8.roc.model.request.RocUserRegisterRequest;
import com.xye8.roc.model.request.UserLoginRequest;
import com.xye8.roc.model.vo.RocUserVO;
import com.xye8.roc.service.RocUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.xye8.roc.constant.UserConstant.USER_LOGIN_STATE;
import static com.xye8.roc.service.impl.RocUserServiceImpl.validateStatus;

@RestController
@RequestMapping("/rocUser")
@CrossOrigin(origins = {"http://localhost:3000"})
@Slf4j
public class RocUserController {
    @Resource
    private RocUserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody RocUserRegisterRequest rocUserRegisterRequest) {
        if (rocUserRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_NULL, "RocUserRegisterRequest is null");
        }
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        BeanUtils.copyProperties(rocUserRegisterRequest, userRegisterDto);
        if (StringUtils.isAnyBlank(userRegisterDto.getUserEmail(), userRegisterDto.getUserPassword(), userRegisterDto.getCheckPassword())) {
            throw new BusinessException(ErrorCode.PARAMS_NULL, "null parameter in RocUserRegisterRequest");
        }
        long result = userService.userRegister(userRegisterDto);
        return ResultUtils.success(result);
    }

    @PostMapping("/login")
    public BaseResponse<RocUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_NULL, "RocUserRegisterRequest is null");
        }
        //        String userEmail = userLoginRequest.getUserEmail();
        //        String userPassword = userLoginRequest.getUserPassword();
        //        if (StringUtils.isAnyBlank(userEmail, userPassword)) {
        //            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        //        }
        RocUser user = userService.userLogin(userLoginRequest, request);
        RocUserVO rocUserVO = new RocUserVO();
        BeanUtils.copyProperties(user, rocUserVO);
        return ResultUtils.success(rocUserVO);
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "request is null");
        }
        int result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    @GetMapping("/current")
    public BaseResponse<RocUserVO> getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        RocUser currentUser = (RocUser) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        long userId = currentUser.getId();

        RocUser user = userService.getById(userId);
        validateStatus(user);

        RocUserVO safetyUser = new RocUserVO();
        BeanUtils.copyProperties(user, safetyUser);
        return ResultUtils.success(safetyUser);
    }

    @GetMapping("/search")
    public BaseResponse<List<RocUserVO>> searchUsers(String nickname, HttpServletRequest request) {
        //        if (!userService.isAdmin(request)) {
        //            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        //        }
        QueryWrapper<RocUser> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(nickname)) {
            queryWrapper.like("nick_name", nickname);
        }
        List<RocUser> userList = userService.list(queryWrapper);
        List<RocUserVO> rocUserVOList = userList.stream().map(user -> {
            RocUserVO rocUserVO = new RocUserVO();
            BeanUtils.copyProperties(user, rocUserVO);
            return rocUserVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(rocUserVOList);
    }

    //    @GetMapping("/search/tags")
    //    public BaseResponse<List<RocUserVO>> searchUsersByTags(List<String> tagList) {
    //        if (CollectionUtils.isEmpty(tagList)) {
    //            throw new BusinessException(ErrorCode.PARAMS_ERROR,"tag list 为空");
    //        }
    //        List<RocUser> userList = userService.searchUsersByTags(tagList);
    //        List<RocUserVO> rocUserVOList = userList.stream()
    //                .map(user -> {
    //                    RocUserVO rocUserVO = new RocUserVO();
    //                    BeanUtils.copyProperties(user, rocUserVO);
    //                    return rocUserVO;
    //                })
    //                .collect(Collectors.toList());
    //        return ResultUtils.success(rocUserVOList);
    //    }

    //    // todo 推荐多个，未实现
    //    @GetMapping("/recommend")
    //    public BaseResponse<Page<User>> recommendUsers(long pageSize, long pageNum, HttpServletRequest request) {
    //        User loginUser = userService.getLoginUser(request);
    //        String redisKey = String.format("yupao:user:recommend:%s", loginUser.getId());
    //        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
    //        // 如果有缓存，直接读缓存
    //        Page<User> userPage = (Page<User>) valueOperations.get(redisKey);
    //        if (userPage != null) {
    //            return ResultUtils.success(userPage);
    //        }
    //        // 无缓存，查数据库
    //        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    //        userPage = userService.page(new Page<>(pageNum, pageSize), queryWrapper);
    //        // 写缓存
    //        try {
    //            valueOperations.set(redisKey, userPage, 30000, TimeUnit.MILLISECONDS);
    //        } catch (Exception e) {
    //            log.error("redis set key error", e);
    //        }
    //        return ResultUtils.success(userPage);
    //    }
    //
    //
    //    @PostMapping("/update")
    //    public BaseResponse<Integer> updateUser(@RequestBody User user, HttpServletRequest request) {
    //        // 校验参数是否为空
    //        if (user == null) {
    //            throw new BusinessException(ErrorCode.PARAMS_ERROR);
    //        }
    //        User loginUser = userService.getLoginUser(request);
    //        int result = userService.updateUser(user, loginUser);
    //        return ResultUtils.success(result);
    //    }
    //
    //    @PostMapping("/delete")
    //    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {
    //        if (!userService.isAdmin(request)) {
    //            throw new BusinessException(ErrorCode.NO_AUTH);
    //        }
    //        if (id <= 0) {
    //            throw new BusinessException(ErrorCode.PARAMS_ERROR);
    //        }
    //        boolean b = userService.removeById(id);
    //        return ResultUtils.success(b);
    //    }
    //
    //    /**
    //     * 获取最匹配的用户
    //     *
    //     * @param num
    //     * @param request
    //     * @return
    //     */
    //    @GetMapping("/match")
    //    public BaseResponse<List<User>> matchUsers(long num, HttpServletRequest request) {
    //        if (num <= 0 || num > 20) {
    //            throw new BusinessException(ErrorCode.PARAMS_ERROR);
    //        }
    //        User user = userService.getLoginUser(request);
    //        return ResultUtils.success(userService.matchUsers(num, user));
    //    }


}