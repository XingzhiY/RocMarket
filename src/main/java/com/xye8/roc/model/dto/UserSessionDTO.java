package com.xye8.roc.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户包装类（脱敏）
 *
 */
@Data
public class UserSessionDTO implements Serializable {
    /**
     * Primary key
     */
    private Integer id;

    /**
     * User nickname
     */
    private String username;

    /**
     * User email
     */
    private String email;

    private static final long serialVersionUID = 1L;
}