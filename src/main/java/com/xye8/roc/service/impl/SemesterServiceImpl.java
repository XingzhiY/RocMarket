package com.xye8.roc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xye8.roc.mapper.SemesterMapper;
import com.xye8.roc.model.domain.Semester;
import com.xye8.roc.service.SemesterService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
* @author Xingzhi Ye
* @description 针对表【semesters】的数据库操作Service实现
* @createDate 2024-08-11 14:59:32
*/
@Service
@Primary
public class SemesterServiceImpl extends ServiceImpl<SemesterMapper, Semester>
    implements SemesterService {

}




