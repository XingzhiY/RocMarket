package com.xye8.roc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xye8.roc.common.ErrorCode;
import com.xye8.roc.exception.BusinessException;
import com.xye8.roc.model.domain.Courses;

import com.xye8.roc.mapper.CoursesMapper;
import com.xye8.roc.model.request.CoursesAddRequest;
import com.xye8.roc.service.CoursesService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Xingzhi Ye
 * @description 针对表【courses】的数据库操作Service实现
 * @createDate 2024-08-11 14:59:32
 */
@Service
public class CoursesServiceImpl extends ServiceImpl<CoursesMapper, Courses> implements CoursesService {

    @Resource
    private CoursesMapper coursesMapper;




    @Override
    public Courses createCourse(CoursesAddRequest coursesAddRequest) {
        String course_code = coursesAddRequest.getCourse_code();
        String course_name = coursesAddRequest.getCourse_name();
        Integer professor_id = coursesAddRequest.getProfessor_id();
        Integer credits = coursesAddRequest.getCredits();

        int count = coursesMapper.countByCourseCode(course_code);
        if (count>0){
            throw new BusinessException(ErrorCode.NO_AUTH,"count= "+count);
        }


        if (professor_id == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "教授ID不能为空");
        }
        if (StringUtils.isAnyBlank(course_code, course_name)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "course_code,course_name有null");
        }
        // 检查是否已有相同课程代码的课程
        //        if (existsByCourseCode(coursesAddRequest.getCourse_code())) {
        //            throw new BusinessException(ErrorCode.PARAMS_ERROR, "课程代码已存在");
        //        }

        // 将 CoursesAddRequest 转换为 Courses 实体
        Courses course = new Courses();
        BeanUtils.copyProperties(coursesAddRequest, course);

        // 保存课程
        int saveResult = coursesMapper.insert(course);
        if (!(saveResult > 0)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "课程添加失败");
        }

        return course;
    }

    @Override
    public int countByCourseCode(String courseCode) {
        return coursesMapper.countByCourseCode(courseCode);

    }


    //    @Override
    public boolean existsByCourseCode(String courseCode) {
        int count = coursesMapper.countByCourseCode(courseCode);
        return count > 0;
    }
}




