package com.xye8.roc.model.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
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




    private static final long serialVersionUID = 1L;
}