package com.xye8.roc.model.request;

import com.xye8.roc.common.ErrorCode;
import com.xye8.roc.exception.BusinessException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @TableName courses
 */
@Data
public class CourseAddRequest implements Serializable {

    @NotBlank(message = "Course code cannot be empty")
    private String course_code;

    @NotBlank(message = "Course name cannot be empty")
    private String course_name;

    @NotNull(message = "Professor ID cannot be null")
    private Integer professor_id;

    // 重写 setCourse_code 方法，去除空格并转换为大写
    public void setCourse_code(String course_code) {
        this.course_code = course_code != null ? StringUtils.deleteWhitespace(course_code.trim()).toUpperCase() : null;
    }

    // 重写 setCourse_name 方法，去除两侧空格
    public void setCourse_name(String course_name) {
        this.course_name = course_name != null ? course_name.trim() : null;
    }

    private static final long serialVersionUID = 1L;
}