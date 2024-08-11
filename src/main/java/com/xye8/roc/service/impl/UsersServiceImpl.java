package com.xye8.roc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xye8.roc.common.ErrorCode;
import com.xye8.roc.constant.UserConstant;
import com.xye8.roc.exception.BusinessException;
import com.xye8.roc.mapper.RocUserMapper;
import com.xye8.roc.model.domain.RocUser;
import com.xye8.roc.model.domain.Users;
import com.xye8.roc.model.dto.UserRegisterDto;
import com.xye8.roc.model.dto.UserSessionDTO;
import com.xye8.roc.model.request.UserLoginRequest;
import com.xye8.roc.model.request.UserRegisterRequest;
import com.xye8.roc.model.vo.UserVO;
import com.xye8.roc.service.UsersService;
import com.xye8.roc.mapper.UsersMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.xye8.roc.constant.SaltConstant.SALT;

/**
 * @author Xingzhi Ye
 * @description 针对表【users】的数据库操作Service实现
 * @createDate 2024-08-11 14:59:32
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {
    @Resource
    private UsersMapper userMapper;

    public static boolean validateEmail(String email) {
        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        if (!matcher.matches()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "email格式不正确");
        }
        return true;
    }

    public static boolean validatePassword(String password) {
        if (password.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码小于8");
        }
        return true;
    }

    public static boolean validateStatus(Users user) {
        if (user.getUser_status() != 0) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "用户状态不正常");
        }
        return true;
    }

    public static boolean validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名不能为空");
        }
        if (username.length() < 2 || username.length() > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名长度必须在2到20个字符之间");
        }
        return true;
    }

    @Override
    public long userRegister(UserRegisterRequest userRegisterRequest) {
        String userEmail = userRegisterRequest.getUserEmail();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String userName = userRegisterRequest.getUserName();

        // 1.
        if (StringUtils.isAnyBlank(userEmail, userPassword, checkPassword, userName)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        // 校验 email
        validateEmail(userEmail);
        //        校验username
        validateUsername(userName);
        //校验密码
        validatePassword(userPassword);

        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入密码不同");
        }
        // 账户不能重复
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", userEmail);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
        }

        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 3. 插入数据
        Users user = new Users();
        user.setEmail(userEmail);
        user.setEncrypted_password(encryptPassword);
        user.setUsername(userName);
        int saveResult = userMapper.insert(user);
        if (saveResult != 1) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"user注册失败");
        }
        return 1;
    }

    @Override
    public Users userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request) {
        String userEmail = userLoginRequest.getUserEmail();
        String userPassword = userLoginRequest.getUserPassword();

        // 1. 参数校验
        if (StringUtils.isAnyBlank(userEmail, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }

        // 2. 校验 email
        validateEmail(userEmail);

        // 3. 校验密码长度
        validatePassword(userPassword);

        // 4. 校验用户是否存在
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", userEmail);
        Users user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在");
        }

        // 5. 校验密码是否正确
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        if (!user.getEncrypted_password().equals(encryptPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
        }

        // 6. 校验用户状态
        validateStatus(user);

        UserSessionDTO userSessionDTO = new UserSessionDTO();
        BeanUtils.copyProperties(user, userSessionDTO);
        // 7. 用户登录成功, 将用户信息存入 session
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, userSessionDTO);

        return user;
    }

    @Override
    public int userLogout(HttpServletRequest request) {
        // 1. 获取当前会话
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "没有session连接");
        }
        // 2. 如果会话存在，则清除会话中的用户信息
        session.removeAttribute(UserConstant.USER_LOGIN_STATE); // 清除用户信息
        session.invalidate(); // 使会话无效

        // 3. 返回状态码，表示成功登出
        return 1; // 返回 1 表示成功
    }

    @Override
    public Users getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN, "用户未登录");
        }
        UserSessionDTO userSessionDTO = (UserSessionDTO) session.getAttribute(UserConstant.USER_LOGIN_STATE);
        if (userSessionDTO == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN, "用户未登录");
        }
//        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("id",userSessionDTO.getId());
        Users user=userMapper.selectById(userSessionDTO.getId());

        return user;
    }
}



