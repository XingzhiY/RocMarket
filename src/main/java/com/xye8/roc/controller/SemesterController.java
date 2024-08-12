package com.xye8.roc.controller;

import com.xye8.roc.model.request.SemestersAddRequest;
import com.xye8.roc.service.SemestersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xye8.roc.model.domain.Semesters;

import com.xye8.roc.common.BaseResponse;
import com.xye8.roc.common.ErrorCode;
import com.xye8.roc.common.ResultUtils;
import com.xye8.roc.exception.BusinessException;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/semesters")
public class SemesterController {


    @Resource
    private SemestersService semestersService;

    // 1. 添加学期
    @PostMapping("/add")
    public BaseResponse<Semesters> addSemester(@RequestBody SemestersAddRequest semestersAddRequest) {
        if (semestersAddRequest == null || semestersAddRequest.getSemester_name() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "学期信息或名称不能为空");
        }

        // 检查学期名称是否重复
        QueryWrapper<Semesters> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("semester_name", semestersAddRequest.getSemester_name());
        long count = semestersService.count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "学期名称已存在");
        }
        Semesters semester = new Semesters();
        BeanUtils.copyProperties(semestersAddRequest, semester);
        boolean saveResult = semestersService.save(semester);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "学期添加失败");
        }

        return ResultUtils.success(semester);
    }

    // 2. 删除学期
    @DeleteMapping("/delete/{id}")
    public BaseResponse<Boolean> deleteSemester(@PathVariable("id") Long id) {
        // 检查ID是否有效
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "无效的学期ID");
        }

        //        // 搜索学期是否存在
        //        Semesters semester = semestersService.getById(id);
        //        if (semester == null) {
        //            throw new BusinessException(ErrorCode.NOT_FOUND, "学期未找到");
        //        }

        // 如果找到，进行删除
        boolean removeResult = semestersService.removeById(id);
        if (!removeResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "学期删除失败");
        }

        return ResultUtils.success(removeResult);
    }


    // 3. 更新学期信息
    @PutMapping("/update/{id}")
    public BaseResponse<Semesters> updateSemester(@PathVariable("id") Long id, @RequestBody SemestersAddRequest semestersAddRequest) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "无效的学期ID");
        }

        if (semestersAddRequest == null || semestersAddRequest.getSemester_name() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "学期信息或名称不能为空");
        }

        // 查找学期是否存在
        Semesters existingSemester = semestersService.getById(id);
        if (existingSemester == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "学期不存在");
        }

        // 更新学期信息
        BeanUtils.copyProperties(semestersAddRequest, existingSemester);

        boolean updateResult = semestersService.updateById(existingSemester);
        if (!updateResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "学期更新失败");
        }

        return ResultUtils.success(existingSemester);
    }

    // 4. 根据ID查询学期
    @GetMapping("/get/{id}")
    public BaseResponse<Semesters> getSemesterById(@PathVariable("id") Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "无效的学期ID");
        }

        Semesters semester = semestersService.getById(id);
        if (semester == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "学期不存在");
        }

        return ResultUtils.success(semester);
    }

    @GetMapping("/getByName")
    public BaseResponse<Semesters> getSemesterByName(@RequestParam("name") String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "学期名称不能为空");
        }

        // 标准化学期名称，将输入名称转换为 "2024spring" 格式
        String standardizedName = standardizeSemesterName(name);

        // 根据标准化后的学期名称查询
        QueryWrapper<Semesters> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("semester_name", standardizedName);
        Semesters semester = semestersService.getOne(queryWrapper);

        if (semester == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "学期不存在");
        }

        return ResultUtils.success(semester);
    }

    private String standardizeSemesterName(String name) {
        // 去除空格并将字符串转换为小写
        String normalized = name.replaceAll("\\s+", "").toLowerCase();

        // 将 "spring2024" 或 "2024spring" 等格式统一转换为 "2024spring"
        if (normalized.matches("^(spring|summer|fall|winter)\\d{4}$")) {
            return normalized.replaceFirst("^(spring|summer|fall|winter)(\\d{4})$", "$2$1");
        }

        return normalized;
    }

    // 5. 查询所有学期
    @GetMapping("/list")
    public BaseResponse<List<Semesters>> listSemesters() {
        List<Semesters> semestersList = semestersService.list();
        return ResultUtils.success(semestersList);
    }

}
