package com.xye8.roc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xye8.roc.common.ErrorCode;
import com.xye8.roc.exception.BusinessException;
import com.xye8.roc.mapper.CourseMapper;
import com.xye8.roc.mapper.ProfessorMapper;
import com.xye8.roc.model.domain.Course;

import com.xye8.roc.model.request.CourseAddRequest;
import com.xye8.roc.service.CourseService;
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
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Resource
    private CourseMapper coursesMapper;

    @Resource
    private ProfessorMapper professorMapper;

    @Override
    public Course createCourse(CourseAddRequest courseAddRequest) {


        // 检查 professor_id 是否存在
        if (professorMapper.selectById(courseAddRequest.getProfessor_id()) == null) {
            log.error("Professor not found. professor_id={}", courseAddRequest.getProfessor_id());
            throw new BusinessException(ErrorCode.NOT_FOUND, "教授不存在");
        }

        // 检查课程代码和教授ID是否重复
        int count = coursesMapper.countByCourseCodeAndProfessor(courseAddRequest.getCourse_code(), courseAddRequest.getProfessor_id());
        if (count > 0) {
            log.error("Duplicate course_code and professor_id. course_code={}, professor_id={}", courseAddRequest.getCourse_code(), courseAddRequest.getProfessor_id());
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "course_code, professor_id重复");
        }

        // 转换并保存课程实体
        Course course = new Course();
        BeanUtils.copyProperties(courseAddRequest, course);

        // 执行保存操作
        int saveResult = coursesMapper.insert(course);
        if (saveResult <= 0) {
            log.error("Failed to add course. course_code={}, professor_id={}", courseAddRequest.getCourse_code(), courseAddRequest.getProfessor_id());
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "课程添加失败");
        }

        log.info("Course added successfully. course_id={}, course_code={}, professor_id={}", course.getId(), courseAddRequest.getCourse_code(), courseAddRequest.getProfessor_id());

        return course;
    }


}




