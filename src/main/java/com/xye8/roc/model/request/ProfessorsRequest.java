package com.xye8.roc.model.request;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName professors
 */
@TableName(value ="professors")
@Data
public class ProfessorsRequest implements Serializable {

    /**
     * Professor name
     */
    private String name;




    private static final long serialVersionUID = 1L;
}