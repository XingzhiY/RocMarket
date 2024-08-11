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
     * Foreign key referencing Courses table
     */
    @TableId
    private Integer course_id;

    /**
     * Foreign key referencing Semesters table
     */
    @TableId
    private Integer semester_id;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}