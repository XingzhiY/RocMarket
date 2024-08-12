package com.xye8.roc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xye8.roc.common.ErrorCode;
import com.xye8.roc.exception.BusinessException;
import com.xye8.roc.model.domain.Reviews;
import com.xye8.roc.mapper.ReviewsMapper;
import com.xye8.roc.model.request.ReviewAddRequest;
import com.xye8.roc.service.ReviewsService;
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
public class ReviewsServiceImpl extends ServiceImpl<ReviewsMapper, Reviews> implements ReviewsService {
    @Resource
    private ReviewsMapper reviewsMapper;

    @Override
    public List<Reviews> getAllReviews() {
        return reviewsMapper.selectAllReviews();
    }

    @Override
    public Reviews getReviewsById(Long id) {
        return reviewsMapper.selectReviewById(id);
    }

    @Override
    public Reviews createReviews(ReviewAddRequest reviewAddRequest) {
        // 参数校验

        if (reviewAddRequest.getUserId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户ID不能为空");
        }

        if (reviewAddRequest.getCourseId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "课程ID不能为空");
        }

        if (reviewAddRequest.getSemesterId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "学期ID不能为空");
        }

        if (reviewAddRequest.getScore() < 0 || reviewAddRequest.getScore() > 100) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "评分必须在0到100之间");
        }

        if (reviewAddRequest.getDifficulty() < 1 || reviewAddRequest.getDifficulty() > 3) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "课程难度必须在1到3之间");
        }

        if (reviewAddRequest.getHomeworkAmount() < 1 || reviewAddRequest.getHomeworkAmount() > 3) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "作业量必须在1到3之间");
        }

        if (reviewAddRequest.getGradingQuality() < 1 || reviewAddRequest.getGradingQuality() > 3) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "评分质量必须在1到3之间");
        }

        if (reviewAddRequest.getLearningGain() < 1 || reviewAddRequest.getLearningGain() > 3) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "学习收获必须在1到3之间");
        }

        // 将 ReviewAddRequest 转换为 Reviews 实体
        Reviews review = new Reviews();
        BeanUtils.copyProperties(reviewAddRequest, review);

        // 插入评论
        int insertResult = reviewsMapper.insert(review);
        if (insertResult <= 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "评论添加失败");
        }

        // 返回插入成功的 Review 对象
        return review;
    }


    @Override
    public Reviews updateReview(Long id, ReviewUpdateRequest reviewUpdateRequest) {
        Reviews existingReview = reviewsMapper.selectReviewById(id);
        if (existingReview == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "评论不存在");
        }
        BeanUtils.copyProperties(reviewUpdateRequest, existingReview);
        reviewsMapper.updateById(existingReview);
        return existingReview;
    }

    @Override
    public boolean deleteReview(Long id) {
        return reviewsMapper.deleteById(id) > 0;
    }
}




