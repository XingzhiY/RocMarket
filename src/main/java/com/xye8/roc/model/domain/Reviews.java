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
 * @TableName reviews
 */
@TableName(value ="reviews")
@Data
public class Reviews implements Serializable {
    /**
     * Primary key
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * Foreign key referencing Users table
     */
    private Integer user_id;

    /**
     * Foreign key referencing Courses table
     */
    private Integer course_id;

    /**
     * Foreign key referencing Semesters table
     */
    private Integer semester_id;

    /**
     * Score given in the review
     */
    private BigDecimal score;

    /**
     * Difficulty of the course, 1=低, 2=中等, 3=高
     */
    private Integer difficulty;

    /**
     * Amount of homework, 1=少, 2=中等, 3=很多
     */
    private Integer homework_amount;

    /**
     * Quality of grading, 1=严苛, 2=一般, 3=宽松
     */
    private Integer grading_quality;

    /**
     * Overall learning gain or experience, 1=很少, 2=一般, 3=很多
     */
    private Integer learning_gain;

    /**
     * Review or comments on the course
     */
    private String review;

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