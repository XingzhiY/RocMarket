package com.xye8.roc.controller;

import com.xye8.roc.model.domain.Course;
import com.xye8.roc.model.request.CourseAddRequest;
import com.xye8.roc.common.BaseResponse;
import com.xye8.roc.common.ErrorCode;
import com.xye8.roc.common.ResultUtils;
import com.xye8.roc.exception.BusinessException;
import com.xye8.roc.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Resource
    private CourseService courseService;

    // 创建新课程
    @PostMapping("/add")
    public BaseResponse<Course> addCourse(@Valid @RequestBody CourseAddRequest courseAddRequest) {
        // 校验请求体中的必要字段
        if (courseAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "coursesAddRequest为空");
        }

        // 调用服务层处理业务逻辑
        Course course = courseService.createCourse(courseAddRequest);

        return ResultUtils.success(course);
    }

    // 2. 删除课程
    @DeleteMapping("/delete/{id}")
    public BaseResponse<Boolean> deleteCourse(@PathVariable("id") Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "无效的课程ID");
        }

        boolean removeResult = courseService.removeById(id);
        if (!removeResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "课程删除失败");
        }

        return ResultUtils.success(removeResult);
    }

    // 3. 更新课程信息
    @PutMapping("/update/{id}")
    public BaseResponse<Course> updateCourse(@PathVariable("id") Long id, @Valid @RequestBody CourseAddRequest courseAddRequest) {
        // 验证课程ID的合法性
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "无效的课程ID");
        }

        // 查找现有课程
        Course existingCourse = courseService.getById(id);
        if (existingCourse == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "课程不存在");
        }

        // 在现有课程的基础上更新信息
        BeanUtils.copyProperties(courseAddRequest, existingCourse);

        // 执行更新操作
        boolean updateResult = courseService.updateById(existingCourse);
        if (!updateResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "课程更新失败");
        }

        // 返回更新后的课程信息
        return ResultUtils.success(existingCourse);
    }


    // 4. 根据ID查询课程
    @GetMapping("/get/{id}")
    public BaseResponse<Course> getCourseById(@PathVariable("id") Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "无效的课程ID");
        }

        Course course = courseService.getById(id);
        if (course == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "课程不存在");
        }

        return ResultUtils.success(course);
    }

    // 5. 查询所有课程
    @GetMapping("/list")
    public BaseResponse<List<Course>> listCourse() {
        List<Course> courseList = courseService.list();
        return ResultUtils.success(courseList);
    }

//    // 6. 根据课程代码查询课程
//    @GetMapping("/getByCode")
//    public BaseResponse<Courses> getCourseByCode(@RequestParam("code") String courseCode) {
//        if (courseCode == null || courseCode.trim().isEmpty()) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "课程代码不能为空");
//        }
//
//        Courses course = coursesService.getByCourseCode(courseCode);
//        if (course == null) {
//            throw new BusinessException(ErrorCode.NOT_FOUND, "课程不存在");
//        }
//
//        return ResultUtils.success(course);
//    }

    // 根据课程代码查询课程数量
//    @GetMapping("/countByCode")
//    public BaseResponse<Integer> countCoursesByCode(@RequestParam("courseCode") String courseCode) {
//        if (courseCode == null || courseCode.trim().isEmpty()) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "课程代码不能为空");
//        }
//
//        int count = coursesService.countByCourseCode(courseCode);
//        return ResultUtils.success(count);
//    }
}
