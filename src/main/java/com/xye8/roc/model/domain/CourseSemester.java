package com.xye8.roc.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName course_semester
 */
@TableName(value ="course_semester")
@Data
public class CourseSemester implements Serializable {
    /**
     * Primary key, auto-incremented
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * Foreign key referencing Course table
     */
    private Integer course_id;

    /**
     * Foreign key referencing Semester table
     */
    private Integer semester_id;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}