package com.xye8.roc.controller;

import com.xye8.roc.model.domain.Courses;
import com.xye8.roc.model.request.CoursesAddRequest;
import com.xye8.roc.common.BaseResponse;
import com.xye8.roc.common.ErrorCode;
import com.xye8.roc.common.ResultUtils;
import com.xye8.roc.exception.BusinessException;
import com.xye8.roc.service.CoursesService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Resource
    private CoursesService coursesService;

    // 创建新课程
    @PostMapping("/add")
    public BaseResponse<Courses> addCourse(@RequestBody CoursesAddRequest coursesAddRequest) {
        // 校验请求体中的必要字段
        if (coursesAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "coursesAddRequest为空");
        }

        // 调用服务层处理业务逻辑
        Courses course = coursesService.createCourse(coursesAddRequest);

        return ResultUtils.success(course);
    }

    // 2. 删除课程
    @DeleteMapping("/delete/{id}")
    public BaseResponse<Boolean> deleteCourse(@PathVariable("id") Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "无效的课程ID");
        }

        boolean removeResult = coursesService.removeById(id);
        if (!removeResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "课程删除失败");
        }

        return ResultUtils.success(removeResult);
    }

    // 3. 更新课程信息
    @PutMapping("/update/{id}")
    public BaseResponse<Courses> updateCourse(@PathVariable("id") Long id, @RequestBody CoursesAddRequest coursesAddRequest) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "无效的课程ID");
        }

        Courses existingCourse = coursesService.getById(id);
        if (existingCourse == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "课程不存在");
        }

        // 更新现有课程的信息
        BeanUtils.copyProperties(coursesAddRequest, existingCourse);

        boolean updateResult = coursesService.updateById(existingCourse);
        if (!updateResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "课程更新失败");
        }

        return ResultUtils.success(existingCourse);
    }

    // 4. 根据ID查询课程
    @GetMapping("/get/{id}")
    public BaseResponse<Courses> getCourseById(@PathVariable("id") Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "无效的课程ID");
        }

        Courses course = coursesService.getById(id);
        if (course == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "课程不存在");
        }

        return ResultUtils.success(course);
    }

    // 5. 查询所有课程
    @GetMapping("/list")
    public BaseResponse<List<Courses>> listCourses() {
        List<Courses> courseList = coursesService.list();
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
    @GetMapping("/countByCode")
    public BaseResponse<Integer> countCoursesByCode(@RequestParam("courseCode") String courseCode) {
        if (courseCode == null || courseCode.trim().isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "课程代码不能为空");
        }

        int count = coursesService.countByCourseCode(courseCode);
        return ResultUtils.success(count);
    }
}
