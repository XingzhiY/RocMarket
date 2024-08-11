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
public class RocUserVO implements Serializable {
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