package com.xye8.roc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xye8.roc.model.domain.Reviews;
import generator.service.ReviewsService;
import com.xye8.roc.mapper.ReviewsMapper;
import org.springframework.stereotype.Service;

/**
* @author Xingzhi Ye
* @description 针对表【reviews】的数据库操作Service实现
* @createDate 2024-08-11 14:59:32
*/
@Service
public class ReviewsServiceImpl extends ServiceImpl<ReviewsMapper, Reviews>
    implements ReviewsService{

}




