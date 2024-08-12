package com.xye8.roc.model.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ReviewAddRequest implements Serializable {


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


    public void setUser_id(Integer user_id) {
        if (user_id == null || user_id <= 0) {
            throw new IllegalArgumentException("用户ID不能为空或无效");
        }
        this.user_id = user_id;
    }

    public void setCourse_id(Integer course_id) {
        if (course_id == null || course_id <= 0) {
            throw new IllegalArgumentException("课程ID不能为空或无效");
        }
        this.course_id = course_id;
    }

    public void setSemester_id(Integer semester_id) {
        if (semester_id == null || semester_id <= 0) {
            throw new IllegalArgumentException("学期ID不能为空或无效");
        }
        this.semester_id = semester_id;
    }

    public void setScore(BigDecimal score) {
        if (score == null || score.compareTo(new BigDecimal("1")) < 0 || score.compareTo(new BigDecimal("5")) > 0) {
            throw new IllegalArgumentException("评分必须在1到5之间");
        }
        this.score = score;
    }

    public void setDifficulty(Integer difficulty) {
        if (difficulty == null || difficulty < 1 || difficulty > 3) {
            throw new IllegalArgumentException("课程难度必须在1到3之间");
        }
        this.difficulty = difficulty;
    }

    public void setHomework_amount(Integer homework_amount) {
        if (homework_amount == null || homework_amount < 1 || homework_amount > 3) {
            throw new IllegalArgumentException("作业量必须在1到3之间");
        }
        this.homework_amount = homework_amount;
    }

    public void setGrading_quality(Integer grading_quality) {
        if (grading_quality == null || grading_quality < 1 || grading_quality > 3) {
            throw new IllegalArgumentException("评分质量必须在1到3之间");
        }
        this.grading_quality = grading_quality;
    }

    public void setLearning_gain(Integer learning_gain) {
        if (learning_gain == null || learning_gain < 1 || learning_gain > 3) {
            throw new IllegalArgumentException("学习收获必须在1到3之间");
        }
        this.learning_gain = learning_gain;
    }

    public void setReview(String review) {
        if (review == null || review.trim().isEmpty()) {
            throw new IllegalArgumentException("评论内容不能为空");
        }
        this.review = review.trim();
    }

    private static final long serialVersionUID = 1L;
}

