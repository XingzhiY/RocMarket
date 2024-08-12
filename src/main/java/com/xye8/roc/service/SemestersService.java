package com.xye8.roc.service;

import com.xye8.roc.model.domain.Semesters;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
* @author Xingzhi Ye
* @description 针对表【semesters】的数据库操作Service
* @createDate 2024-08-11 14:59:32
*/
@Service

public interface SemestersService extends IService<Semesters> {

}
