package com.xye8.roc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xye8.roc.common.ErrorCode;
import com.xye8.roc.exception.BusinessException;
import com.xye8.roc.model.domain.RocUser;
import com.xye8.roc.model.dto.UserRegisterDto;
import com.xye8.roc.model.request.UserLoginRequest;
import com.xye8.roc.model.vo.RocUserVO;
import com.xye8.roc.service.RocUserService;
import com.xye8.roc.mapper.RocUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.xye8.roc.constant.SaltConstant.SALT;
import static com.xye8.roc.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author Xingzhi Ye
 * @description 针对表【roc_user(用户信息表)】的数据库操作Service实现
 * @createDate 2024-08-07 21:07:43
 */
@Service
@Slf4j
public class RocUserServiceImpl extends ServiceImpl<RocUserMapper, RocUser> implements RocUserService {
    @Resource
    private RocUserMapper userMapper;

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
    public static boolean validateStatus(RocUser user) {
        if (user.getStatus()!=0) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "用户状态不正常");
        }
        return true;
    }

    @Override
    public long userRegister(UserRegisterDto userRegisterDto) {
        String userEmail = userRegisterDto.getUserEmail();
        String userPassword = userRegisterDto.getUserPassword();
        String checkPassword = userRegisterDto.getCheckPassword();

        // 1.
        if (StringUtils.isAnyBlank(userEmail, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        //        校验 email
        validateEmail(userEmail);
        //校验密码
        validatePassword(userPassword);

        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入密码不同");
        }
        // 账户不能重复
        QueryWrapper<RocUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", userEmail);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
        }

        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 3. 插入数据
        RocUser user = new RocUser();
        user.setEmail(userEmail);
        user.setPassword(encryptPassword);
        int saveResult = userMapper.insert(user);
        if (saveResult != 1) {
            return -1;
        }
        return 1;
    }

    @Override
    public RocUser userLogin(UserLoginRequest userLoginRequest,HttpServletRequest request) {
        String userEmail = userLoginRequest.getUserEmail();
        String userPassword = userLoginRequest.getUserPassword();


        // 1. 校验
        if (StringUtils.isAnyBlank(userEmail, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "userEmail, userPassword有null");
        }
        validateEmail(userEmail);
        // 账户不能包含特殊字符
        validatePassword(userPassword);
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        QueryWrapper<RocUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", userEmail);
        queryWrapper.eq("password", encryptPassword);
        RocUser user = userMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在");
        }
        validateStatus(user);
        // 3. 用户脱敏
        RocUserVO rocUserVO = new RocUserVO();
        BeanUtils.copyProperties(user, rocUserVO);
        // 4. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, rocUserVO);
        return user;


    }

    /**
     * 用户注销
     *
     * @param request
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }

//    /**
//     * 根据标签搜索用户（内存过滤）
//     *
//     * @param tagList 用户要拥有的标签
//     * @return
//     */
//    @Override
//    public List<RocUser> searchUsersByTags(List<String> tagList) {
//        if (CollectionUtils.isEmpty(tagList)) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        // 1. 先查询所有用户
//        QueryWrapper<RocUser> queryWrapper = new QueryWrapper<>();
//        List<RocUser> userList = userMapper.selectList(queryWrapper);
//        Gson gson = new Gson();
//        // 2. 在内存中判断是否包含要求的标签
//        return userList.stream().filter(user -> {
//            //写成 string 形式的 jason。
//            String tagsStr = user.getTags();
//            //反序列化 from jason 的第2个参数到我们目标 Java 类型的类。
//            Set<String> tempTagNameSet = gson.fromJson(tagsStr, new TypeToken<Set<String>>() {
//            }.getType());
//            //
//            tempTagNameSet = Optional.ofNullable(tempTagNameSet).orElse(new HashSet<>());
//            for (String tagName : tagList) {
//                if (!tempTagNameSet.contains(tagName)) {
//                    return false;
//                }
//            }
//            return true;
//        }).map(this::getSafetyUser).collect(Collectors.toList());
//    }

//    @Override
//    public int updateUser(User user, User loginUser) {
//        long userId = user.getId();
//        if (userId <= 0) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        // todo 补充校验，如果用户没有传任何要更新的值，就直接报错，不用执行 update 语句
//        // 如果是管理员，允许更新任意用户
//        // 如果不是管理员，只允许更新当前（自己的）信息
//        if (!isAdmin(loginUser) && userId != loginUser.getId()) {
//            throw new BusinessException(ErrorCode.NO_AUTH);
//        }
//        User oldUser = userMapper.selectById(userId);
//        if (oldUser == null) {
//            throw new BusinessException(ErrorCode.NULL_ERROR);
//        }
//        return userMapper.updateById(user);
//    }
//
//    @Override
//    public User getLoginUser(HttpServletRequest request) {
//        if (request == null) {
//            return null;
//        }
//        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
//        if (userObj == null) {
//            throw new BusinessException(ErrorCode.NO_AUTH);
//        }
//        return (User) userObj;
//    }
//
//    /**
//     * 是否为管理员
//     *
//     * @param request
//     * @return
//     */
//    @Override
//    public boolean isAdmin(HttpServletRequest request) {
//        // 仅管理员可查询
//        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
//        User user = (User) userObj;
//        return user != null && user.getUserRole() == UserConstant.ADMIN_ROLE;
//    }
//
//    /**
//     * 是否为管理员
//     *
//     * @param loginUser
//     * @return
//     */
//    @Override
//    public boolean isAdmin(User loginUser) {
//        return loginUser != null && loginUser.getUserRole() == UserConstant.ADMIN_ROLE;
//    }
//
//    @Override
//    public List<User> matchUsers(long num, User loginUser) {
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.select("id", "tags");
//        queryWrapper.isNotNull("tags");
//        List<User> userList = this.list(queryWrapper);
//        String tags = loginUser.getTags();
//        Gson gson = new Gson();
//        List<String> tagList = gson.fromJson(tags, new TypeToken<List<String>>() {
//        }.getType());
//        // 用户列表的下标 => 相似度
//        List<Pair<User, Long>> list = new ArrayList<>();
//        // 依次计算所有用户和当前用户的相似度
//        for (int i = 0; i < userList.size(); i++) {
//            User user = userList.get(i);
//            String userTags = user.getTags();
//            // 无标签或者为当前用户自己
//            if (StringUtils.isBlank(userTags) || user.getId() == loginUser.getId()) {
//                continue;
//            }
//            List<String> userTagList = gson.fromJson(userTags, new TypeToken<List<String>>() {
//            }.getType());
//            // 计算分数
//            long distance = AlgorithmUtils.minDistance(tagList, userTagList);
//            list.add(new Pair<>(user, distance));
//        }
//        // 按编辑距离由小到大排序
//        List<Pair<User, Long>> topUserPairList = list.stream()
//                .sorted((a, b) -> (int) (a.getValue() - b.getValue()))
//                .limit(num)
//                .collect(Collectors.toList());
//        // 原本顺序的 userId 列表
//        List<Long> userIdList = topUserPairList.stream().map(pair -> pair.getKey().getId()).collect(Collectors.toList());
//        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
//        userQueryWrapper.in("id", userIdList);
//        // 1, 3, 2
//        // User1、User2、User3
//        // 1 => User1, 2 => User2, 3 => User3
//        Map<Long, List<User>> userIdUserListMap = this.list(userQueryWrapper)
//                .stream()
//                .map(user -> getSafetyUser(user))
//                .collect(Collectors.groupingBy(User::getId));
//        List<User> finalUserList = new ArrayList<>();
//        for (Long userId : userIdList) {
//            finalUserList.add(userIdUserListMap.get(userId).get(0));
//        }
//        return finalUserList;
//    }
//
//    /**
//     * 根据标签搜索用户（SQL 查询版）
//     *
//     * @param tagNameList 用户要拥有的标签
//     * @return
//     */
//    @Deprecated
//    private List<User> searchUsersByTagsBySQL(List<String> tagNameList) {
//        if (CollectionUtils.isEmpty(tagNameList)) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        // 拼接 and 查询
//        // like '%Java%' and like '%Python%'
//        for (String tagName : tagNameList) {
//            queryWrapper = queryWrapper.like("tags", tagName);
//        }
//        List<User> userList = userMapper.selectList(queryWrapper);
//        return userList.stream().map(this::getSafetyUser).collect(Collectors.toList());
//    }

}




