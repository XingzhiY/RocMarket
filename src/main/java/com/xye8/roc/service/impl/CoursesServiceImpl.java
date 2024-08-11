package com.xye8.roc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xye8.roc.model.domain.Courses;

import com.xye8.roc.mapper.CoursesMapper;
import com.xye8.roc.service.CoursesService;
import org.springframework.stereotype.Service;

/**
* @author Xingzhi Ye
* @description 针对表【courses】的数据库操作Service实现
* @createDate 2024-08-11 14:59:32
*/
@Service
public class CoursesServiceImpl extends ServiceImpl<CoursesMapper, Courses>
    implements CoursesService {

}




