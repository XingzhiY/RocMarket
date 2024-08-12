package com.xye8.roc.service;

import com.xye8.roc.model.domain.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xye8.roc.model.request.CourseAddRequest;

/**
* @author Xingzhi Ye
* @description 针对表【courses】的数据库操作Service
* @createDate 2024-08-11 14:59:32
*/
public interface CourseService extends IService<Course> {

    Course createCourse(CourseAddRequest courseAddRequest);


}
