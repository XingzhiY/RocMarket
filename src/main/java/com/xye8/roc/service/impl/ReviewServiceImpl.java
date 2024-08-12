package com.xye8.roc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xye8.roc.common.ErrorCode;
import com.xye8.roc.exception.BusinessException;
import com.xye8.roc.mapper.ReviewMapper;
import com.xye8.roc.model.domain.Review;
import com.xye8.roc.model.request.ReviewAddRequest;
import com.xye8.roc.service.ReviewService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Xingzhi Ye
 * @description 针对表【reviews】的数据库操作Service实现
 * @createDate 2024-08-11 14:59:32
 */
@Service
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review> implements ReviewService {
    @Resource
    private ReviewMapper reviewsMapper;



    @Override
    public Review createReview(ReviewAddRequest reviewAddRequest) {
        // 参数校验



        // 将 ReviewAddRequest 转换为 Reviews 实体
        Review review = new Review();
        BeanUtils.copyProperties(reviewAddRequest, review);

        // 插入评论
        int insertResult = reviewsMapper.insert(review);
        if (insertResult <= 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "评论添加失败");
        }

        // 返回插入成功的 Review 对象
        return review;
    }


}




