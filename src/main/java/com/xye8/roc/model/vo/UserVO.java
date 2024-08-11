package com.xye8.roc.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户包装类（脱敏）
 *
 */
@Data
public class UserVO implements Serializable {
    /**
     * Primary key
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * User nickname
     */
    private String username;

    /**
     * User email
     */
    private String email;


    /**
     * User bio/description
     */
    private String bio;

    /**
     * User personal website
     */
    private String personal_website;

    /**
     * User contact information
     */
    private String contact_info;

    /**
     * Status: 0 - Active, 1 - Banned
     */
    private Integer user_status;

    /**
     * User role: 0 - Regular User, 1 - Administrator
     */
    private Integer user_role;

    /**
     * Soft delete flag
     */
    private Integer is_deleted;

    /**
     * Record creation timestamp
     */
    private Date created_at;

    /**
     * Record update timestamp
     */
    private Date updated_at;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}