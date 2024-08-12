package com.xye8.roc.model.request;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 
 * @TableName professors
 */
@TableName(value ="professors")
@Data
public class ProfessorRequest implements Serializable {

    /**
     * Professor name
     */
    @NotBlank
    private String name;




    private static final long serialVersionUID = 1L;
}