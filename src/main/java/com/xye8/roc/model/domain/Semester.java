package com.xye8.roc.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName semester
 */
@TableName(value ="semester")
@Data
public class Semester implements Serializable {
    /**
     * Primary key, auto-incremented
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * Semester name, e.g., 2024春
     */
    private String semester_name;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}