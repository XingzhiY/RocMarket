package com.xye8.roc.model.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName professors
 */
@TableName(value ="professors")
@Data
public class ProfessorsVO implements Serializable {
    /**
     * Primary key
     */
    private Integer id;

    /**
     * Professor name
     */
    private String name;




    private static final long serialVersionUID = 1L;
}