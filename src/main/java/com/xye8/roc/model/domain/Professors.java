package com.xye8.roc.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName professors
 */
@TableName(value ="professors")
@Data
public class Professors implements Serializable {
    /**
     * Primary key
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * Professor name
     */
    private String name;

    /**
     * Average grade given by the professor
     */
    private BigDecimal average_grade;

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