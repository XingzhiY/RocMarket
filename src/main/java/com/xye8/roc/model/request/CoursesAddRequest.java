package com.xye8.roc.model.request;

import com.xye8.roc.common.ErrorCode;
import com.xye8.roc.exception.BusinessException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @TableName courses
 */
@Data
public class CoursesAddRequest implements Serializable {


    /**
     * Course code
     */
    private String course_code;

    /**
     * Course name
     */
    private String course_name;

    /**
     * Foreign key referencing Professors table
     */
    private Integer professor_id;

    /**
     * Number of credits
     */
    private Integer credits;

    // 重写 setCourse_code 方法，去除空格并转换为大写
    public void setCourse_code(String course_code) {
        this.course_code = course_code != null ? StringUtils.deleteWhitespace(course_code.trim()).toUpperCase() : null;
    }

    // 重写 setCourse_name 方法，去除两侧空格
    public void setCourse_name(String course_name) {
        this.course_name = course_name != null ? course_name.trim() : null;
    }

    // 重写 setCredits 方法，可以在此处添加进一步的验证逻辑
    public void setCredits(Integer credits) {
        if (credits != null && (credits < 1 || credits > 10)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"课程学分无效，应在1到10之间");
        }
        this.credits = credits;
    }


    private static final long serialVersionUID = 1L;
}