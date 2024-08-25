package com.xye8.roc.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xye8.roc.model.domain.Course;
import com.xye8.roc.model.request.ProfessorRequest;
import com.xye8.roc.service.CourseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.xye8.roc.model.domain.Professor;
import com.xye8.roc.service.ProfessorService;
import com.xye8.roc.common.BaseResponse;
import com.xye8.roc.common.ErrorCode;
import com.xye8.roc.common.ResultUtils;
import com.xye8.roc.exception.BusinessException;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/professor")
public class ProfessorController {

    @Resource
    private ProfessorService professorService;
    @Resource
    private CourseService courseService;

    // 1. 添加教授
    @PostMapping("/add")
    public BaseResponse<Professor> addProfessor(@Valid @RequestBody ProfessorRequest professorRequest) {
        if (professorRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "教授信息或名字不能为空");
        }

        // 查重：检查是否已存在同名教授
        QueryWrapper<Professor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", professorRequest.getName());
        long count = professorService.count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "教授已存在");
        }

        // 如果不存在，则进行保存操作
        Professor professor = new Professor();
        BeanUtils.copyProperties(professorRequest, professor);
        boolean saveResult = professorService.save(professor);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "教授添加失败");
        }

        // 转换为 VO 对象并返回
//        Professors professorsVO = new Professors();
//        BeanUtils.copyProperties(professors, professorsVO);

        return ResultUtils.success(professor);
    }

    // 2. 删除教授
    @DeleteMapping("/delete/{id}")
    public BaseResponse<Professor> deleteProfessor(@PathVariable("id") Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "无效的教授ID");
        }

        // 查找教授是否存在
        Professor professor = professorService.getById(id);
        if (professor == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "教授不存在");
        }

        // 删除教授
        boolean removeResult = professorService.removeById(id);
        if (!removeResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "教授删除失败");
        }

        // 将删除的教授信息转换为 VO 对象并返回
//        Professors professorsVO = new Professors();
//        BeanUtils.copyProperties(professor, professorsVO);

        return ResultUtils.success(professor);
    }


    // 3. 更新教授信息
    @PutMapping("/update/{id}")
    public BaseResponse<Professor> updateProfessor(@PathVariable("id") Long id, @Valid @RequestBody ProfessorRequest professorRequest) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "无效的教授ID");
        }

        if (professorRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "教授信息不能为空");
        }

        // 查找教授是否存在
        Professor existingProfessor = professorService.getById(id);
        if (existingProfessor == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "教授不存在");
        }

//        Professor professor =new Professor();
        // 更新教授信息
        BeanUtils.copyProperties(professorRequest, existingProfessor);

        // 执行更新操作
        boolean updateResult = professorService.updateById(existingProfessor);
        if (!updateResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "教授更新失败");
        }

        // 将更新后的教授信息转换为 VO 对象并返回
//        Professors professorsVO = new Professors();
//        BeanUtils.copyProperties(professors, professorsVO);

        return ResultUtils.success(existingProfessor);
    }



    // 4. 根据ID查询教授
    @GetMapping("/get/{id}")
    public BaseResponse<Professor> getProfessorById(@PathVariable("id") Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "无效的教授ID");
        }

        Professor professor = professorService.getById(id);
        if (professor == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "教授不存在");
        }

        return ResultUtils.success(professor);
    }
    @GetMapping("/searchByName")
    public BaseResponse<List<Professor>> searchProfessorByName(@RequestParam("name") String name) {
        if (StringUtils.isBlank(name)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "教授姓名不能为空");
        }

        // 根据教授姓名查找教授信息
        QueryWrapper<Professor> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        List<Professor> professorList = professorService.list(queryWrapper);

        if (professorList == null || professorList.isEmpty()) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "未找到匹配的教授");
        }

//        // 将 Professors 列表转换为 Professors 列表
//        List<Professors> professorsVOList = professorsList.stream()
//                .map(professor -> {
//                    Professors professorsVO = new Professors();
//                    BeanUtils.copyProperties(professor, professorsVO);
//                    return professorsVO;
//                })
//                .collect(Collectors.toList());

        return ResultUtils.success(professorList);
    }

    // 5. 查询所有教授
    @GetMapping("/list")
    public BaseResponse<List<Professor>> listProfessors() {
        // 获取所有教授列表
        List<Professor> professorList = professorService.list();

//        // 将 Professors 转换为 Professors
//        List<Professors> professorsVOList = professorsList.stream()
//                .map(professor -> {
//                    Professors professorsVO = new Professors();
//                    BeanUtils.copyProperties(professor, professorsVO);
//                    return professorsVO;
//                })
//                .collect(Collectors.toList());

        return ResultUtils.success(professorList);
    }
    @GetMapping("/listCourses/{id}")
    public BaseResponse<List<Course>> listProfessorCourses(@PathVariable("id") Long id) {
        // 获取所有教授列表

        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "无效的教授ID");
        }

        Professor professor = professorService.getById(id);
        if (professor == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "教授不存在");
        }
        QueryWrapper<Course> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("professor_id",id);
        List<Course> list=courseService.list(queryWrapper);
        if (list == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "没有教授的课程");
        }
        return ResultUtils.success(list);
    }

}
