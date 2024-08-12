package com.xye8.roc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xye8.roc.common.ErrorCode;
import com.xye8.roc.exception.BusinessException;
import com.xye8.roc.model.domain.Courses;

import com.xye8.roc.mapper.CoursesMapper;
import com.xye8.roc.model.request.CoursesAddRequest;
import com.xye8.roc.service.CoursesService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CoursesServiceImpl extends ServiceImpl<CoursesMapper, Courses> implements CoursesService {

    @Resource
    private CoursesMapper coursesMapper;


    @Override
    public Courses createCourse(CoursesAddRequest coursesAddRequest) {
        // 处理和验证请求中的参数


        // 参数校验
        if (coursesAddRequest.getProfessor_id() == null) {
            log.error("Professor ID is null.");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "教授ID不能为空");
        }

        if (StringUtils.isAnyBlank(coursesAddRequest.getCourse_code(), coursesAddRequest.getCourse_name())) {
            log.error("Course code or course name is null or empty. course_code={}, course_name={}",
                    coursesAddRequest.getCourse_code(), coursesAddRequest.getCourse_name());
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "course_code, course_name不能为空");
        }

        // 检查课程学分是否合理（假设课程学分范围为1-10）
        if (coursesAddRequest.getCredits() == null || coursesAddRequest.getCredits() < 1 || coursesAddRequest.getCredits() > 10) {
            log.error("Invalid credits value: {}", coursesAddRequest.getCredits());
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "课程学分无效，应在1到10之间");
        }

        // 检查课程代码和教授ID是否重复
        int count = coursesMapper.countByCourseCodeAndProfessor(coursesAddRequest.getCourse_code(), coursesAddRequest.getProfessor_id());
        if (count > 0) {
            log.error("Duplicate course_code and professor_id. course_code={}, professor_id={}",
                    coursesAddRequest.getCourse_code(), coursesAddRequest.getProfessor_id());
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "course_code, professor_id重复");
        }

        // 转换并保存课程实体
        Courses course = new Courses();
        BeanUtils.copyProperties(coursesAddRequest, course);

        // 执行保存操作
        int saveResult = coursesMapper.insert(course);
        if (saveResult <= 0) {
            log.error("Failed to add course. course_code={}, professor_id={}",
                    coursesAddRequest.getCourse_code(), coursesAddRequest.getProfessor_id());
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "课程添加失败");
        }

        log.info("Course added successfully. course_id={}, course_code={}, professor_id={}",
                course.getId(), coursesAddRequest.getCourse_code(), coursesAddRequest.getProfessor_id());

        return course;
    }


}




