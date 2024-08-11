package com.xye8.roc.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName course_semesters
 */
@TableName(value ="course_semesters")
@Data
public class CourseSemesters implements Serializable {
    /**
     * Primary key, auto-incremented
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * Foreign key referencing Courses table
     */
    private Integer course_id;

    /**
     * Foreign key referencing Semesters table
     */
    private Integer semester_id;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}