package com.xye8.roc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xye8.roc.model.domain.Professors;
import generator.service.ProfessorsService;
import com.xye8.roc.mapper.ProfessorsMapper;
import org.springframework.stereotype.Service;

/**
* @author Xingzhi Ye
* @description 针对表【professors】的数据库操作Service实现
* @createDate 2024-08-11 14:59:32
*/
@Service
public class ProfessorsServiceImpl extends ServiceImpl<ProfessorsMapper, Professors>
    implements ProfessorsService{

}




