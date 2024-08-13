package com.xye8.roc.model.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ReviewAddRequest implements Serializable {


    /**
     * Foreign key referencing User table
     */
    @NotNull(message = "用户ID不能为空")
    @Min(value = 1, message = "用户ID必须大于0")
    private Integer user_id;

    /**
     * Foreign key referencing Course table
     */
    @NotNull(message = "课程ID不能为空")
    @Min(value = 1, message = "课程ID必须大于0")
    private Integer course_id;

    /**
     * Foreign key referencing Semester table
     */
    @NotNull(message = "学期ID不能为空")
    @Min(value = 1, message = "学期ID必须大于0")
    private Integer semester_id;

    /**
     * Score given in the review 1-5
     */
    @NotNull(message = "评分不能为空")
    @Min(value = 1, message = "评分必须在1到5之间")
    @Max(value = 5, message = "评分必须在1到5之间")
    private Integer score;

    /**
     * Difficulty of the course, 1=低, 2=中等, 3=高
     */
    @NotNull(message = "课程难度不能为空")
    @Min(value = 1, message = "课程难度必须在1到3之间")
    @Max(value = 3, message = "课程难度必须在1到3之间")
    private Integer difficulty;

    /**
     * Amount of homework, 1=少, 2=中等, 3=很多
     */
    @NotNull(message = "作业量不能为空")
    @Min(value = 1, message = "作业量必须在1到3之间")
    @Max(value = 3, message = "作业量必须在1到3之间")
    private Integer homework_amount;

    /**
     * Quality of grading, 1=严苛, 2=一般, 3=宽松
     */
    @NotNull(message = "评分质量不能为空")
    @Min(value = 1, message = "评分质量必须在1到3之间")
    @Max(value = 3, message = "评分质量必须在1到3之间")
    private Integer grading_quality;

    /**
     * Overall learning gain or experience, 1=很少, 2=一般, 3=很多
     */
    @NotNull(message = "学习收获不能为空")
    @Min(value = 1, message = "学习收获必须在1到3之间")
    @Max(value = 3, message = "学习收获必须在1到3之间")
    private Integer learning_gain;

    /**
     * Review or comments on the course
     */
    @NotBlank(message = "评论内容不能为空")
    @Size(max = 10000, message = "评论内容不能超过10000个字符")
    private String review;

    public void setReview(String review) {

        this.review = review.trim();
    }

    private static final long serialVersionUID = 1L;
}

