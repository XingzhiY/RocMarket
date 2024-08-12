package com.xye8.roc.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xye8.roc.model.request.ProfessorsRequest;
import com.xye8.roc.model.vo.ProfessorsVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.xye8.roc.model.domain.Professors;
import com.xye8.roc.service.ProfessorsService;
import com.xye8.roc.common.BaseResponse;
import com.xye8.roc.common.ErrorCode;
import com.xye8.roc.common.ResultUtils;
import com.xye8.roc.exception.BusinessException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/professors")
public class ProfessorController {

    @Autowired
    private ProfessorsService professorsService;

    // 1. 添加教授
    @PostMapping("/add")
    public BaseResponse<ProfessorsVO> addProfessor(@RequestBody ProfessorsRequest professorsRequest) {
        if (professorsRequest == null || professorsRequest.getName() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "教授信息或名字不能为空");
        }

        // 查重：检查是否已存在同名教授
        QueryWrapper<Professors> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", professorsRequest.getName());
        long count = professorsService.count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "教授已存在");
        }

        // 如果不存在，则进行保存操作
        Professors professors = new Professors();
        BeanUtils.copyProperties(professorsRequest, professors);
        boolean saveResult = professorsService.save(professors);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "教授添加失败");
        }

        // 转换为 VO 对象并返回
        ProfessorsVO professorsVO = new ProfessorsVO();
        BeanUtils.copyProperties(professors, professorsVO);

        return ResultUtils.success(professorsVO);
    }

    // 2. 删除教授
    @DeleteMapping("/delete/{id}")
    public BaseResponse<ProfessorsVO> deleteProfessor(@PathVariable("id") Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "无效的教授ID");
        }

        // 查找教授是否存在
        Professors professor = professorsService.getById(id);
        if (professor == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "教授不存在");
        }

        // 删除教授
        boolean removeResult = professorsService.removeById(id);
        if (!removeResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "教授删除失败");
        }

        // 将删除的教授信息转换为 VO 对象并返回
        ProfessorsVO professorsVO = new ProfessorsVO();
        BeanUtils.copyProperties(professor, professorsVO);

        return ResultUtils.success(professorsVO);
    }


    // 3. 更新教授信息
    @PutMapping("/update/{id}")
    public BaseResponse<ProfessorsVO> updateProfessor(@PathVariable("id") Long id, @RequestBody ProfessorsRequest professorsRequest) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "无效的教授ID");
        }

        if (professorsRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "教授信息不能为空");
        }

        // 查找教授是否存在
        Professors existingProfessor = professorsService.getById(id);
        if (existingProfessor == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "教授不存在");
        }

        // 更新教授信息
        BeanUtils.copyProperties(professorsRequest, existingProfessor);

        // 执行更新操作
        boolean updateResult = professorsService.updateById(existingProfessor);
        if (!updateResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "教授更新失败");
        }

        // 将更新后的教授信息转换为 VO 对象并返回
        ProfessorsVO professorsVO = new ProfessorsVO();
        BeanUtils.copyProperties(existingProfessor, professorsVO);

        return ResultUtils.success(professorsVO);
    }



    // 4. 根据ID查询教授
    @GetMapping("/get/{id}")
    public BaseResponse<Professors> getProfessorById(@PathVariable("id") Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "无效的教授ID");
        }

        Professors professor = professorsService.getById(id);
        if (professor == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "教授不存在");
        }

        return ResultUtils.success(professor);
    }
    @GetMapping("/searchByName")
    public BaseResponse<List<ProfessorsVO>> searchProfessorByName(@RequestParam("name") String name) {
        if (StringUtils.isBlank(name)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "教授姓名不能为空");
        }

        // 根据教授姓名查找教授信息
        QueryWrapper<Professors> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        List<Professors> professorsList = professorsService.list(queryWrapper);

        if (professorsList == null || professorsList.isEmpty()) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "未找到匹配的教授");
        }

        // 将 Professors 列表转换为 ProfessorsVO 列表
        List<ProfessorsVO> professorsVOList = professorsList.stream()
                .map(professor -> {
                    ProfessorsVO professorsVO = new ProfessorsVO();
                    BeanUtils.copyProperties(professor, professorsVO);
                    return professorsVO;
                })
                .collect(Collectors.toList());

        return ResultUtils.success(professorsVOList);
    }

    // 5. 查询所有教授
    @GetMapping("/list")
    public BaseResponse<List<ProfessorsVO>> listProfessors() {
        // 获取所有教授列表
        List<Professors> professorsList = professorsService.list();

        // 将 Professors 转换为 ProfessorsVO
        List<ProfessorsVO> professorsVOList = professorsList.stream()
                .map(professor -> {
                    ProfessorsVO professorsVO = new ProfessorsVO();
                    BeanUtils.copyProperties(professor, professorsVO);
                    return professorsVO;
                })
                .collect(Collectors.toList());

        return ResultUtils.success(professorsVOList);
    }

}
