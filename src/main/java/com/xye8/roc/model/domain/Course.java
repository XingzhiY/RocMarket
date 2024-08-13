package com.xye8.roc.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName course
 */
@TableName(value ="course")
@Data
public class Course implements Serializable {
    /**
     * Primary key
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * Course code
     */
    private String course_code;

    /**
     * Course name
     */
    private String course_name;

    /**
     * Foreign key referencing Professor table
     */
    private Integer professor_id;

    /**
     * Number of credits 1-10
     */
    private Integer credits;

    /**
     * Difficulty level of the course, 1=简单, 2=中等, 3=困难
     */
    private Integer difficulty;

    /**
     * Amount of homework, 1=很少, 2=中等, 3=超多
     */
    private Integer homework_amount;

    /**
     * Fairness or leniency of grading, 1=超坏, 2=一般, 3=超好
     */
    private Integer grading_fairness;

    /**
     * Overall learning gain or experience, 1=很少, 2=一般, 3=很多
     */
    private Integer learning_gain;

    /**
     * Average score of the course 1-5
     */
    private Integer average_score;

    /**
     * Number of reviews
     */
    private Integer number_of_reviews;

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