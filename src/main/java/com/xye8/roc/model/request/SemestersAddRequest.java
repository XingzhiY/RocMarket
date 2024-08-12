package com.xye8.roc.model.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName semesters
 */
@TableName(value ="semesters")
@Data
public class SemestersAddRequest implements Serializable {

    /**
     * Semester name, e.g., 2024春
     */
    private String semester_name;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}