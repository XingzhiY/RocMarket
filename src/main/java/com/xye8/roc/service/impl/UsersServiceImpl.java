package com.xye8.roc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xye8.roc.common.ErrorCode;
import com.xye8.roc.exception.BusinessException;
import com.xye8.roc.mapper.RocUserMapper;
import com.xye8.roc.model.domain.RocUser;
import com.xye8.roc.model.domain.Users;
import com.xye8.roc.model.dto.UserRegisterDto;
import com.xye8.roc.model.request.UserRegisterRequest;
import com.xye8.roc.service.UsersService;
import com.xye8.roc.mapper.UsersMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
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


    @Override
    public long userRegister(UserRegisterRequest userRegisterRequest) {
        String userEmail = userRegisterRequest.getUserEmail();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();

        // 1.
        if (StringUtils.isAnyBlank(userEmail, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        // 校验 email
        validateEmail(userEmail);
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
        int saveResult = userMapper.insert(user);
        if (saveResult != 1) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"user注册失败");
        }
        return 1;
    }
}




