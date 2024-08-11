package com.xye8.roc.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户信息表
 * @TableName roc_user
 */
@TableName(value ="roc_user")
@Data
public class RocUser implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户昵称
     */
    private String nick_name;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态 (0 - 正常, 1 - 封禁)
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date create_time;

    /**
     * 更新时间
     */
    private Date update_time;

    /**
     * 用户角色 (0 - 用户, 1 - 管理员, 2 - VIP)
     */
    private Integer user_role;

    /**
     * 标签 JSON 列表
     */
    private Object tags;

    /**
     * 联系方式
     */
    private String contact_info;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer is_delete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}