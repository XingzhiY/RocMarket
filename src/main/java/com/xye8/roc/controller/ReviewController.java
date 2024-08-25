package com.xye8.roc.controller;

import com.xye8.roc.common.BaseResponse;
import com.xye8.roc.common.ErrorCode;
import com.xye8.roc.common.ResultUtils;
import com.xye8.roc.exception.BusinessException;
import com.xye8.roc.model.domain.Review;
import com.xye8.roc.model.request.ReviewAddRequest;
import com.xye8.roc.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Resource
    private ReviewService reviewService;

    // 获取所有评论
    @GetMapping
    public BaseResponse<List<Review>> getAllReviews() {
        List<Review> reviews = reviewService.list();
        return ResultUtils.success(reviews);
    }

    // 根据ID获取评论
    @GetMapping("/{id}")
    public BaseResponse<Review> getReviewById(@PathVariable Long id) {
        Review review = reviewService.getById(id);
        if (review == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "评论不存在");
        }
        return ResultUtils.success(review);
    }

    // 创建评论
    @PostMapping
    public BaseResponse<Review> createReview(@Valid @RequestBody ReviewAddRequest reviewAddRequest) {
        if (reviewAddRequest == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "reviewAddRequest = null");
        }
        Review review = reviewService.createReview(reviewAddRequest);
        return ResultUtils.success(review);
    }

    @PutMapping("/update/{id}")
    public BaseResponse<Review> updateReview(@PathVariable Long id, @Valid @RequestBody ReviewAddRequest reviewAddRequest) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "无效的ID");
        }
        if (reviewAddRequest == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "reviewAddRequest = null");
        }
        Review review = reviewService.updateReview(id, reviewAddRequest);
        return ResultUtils.success(review);
    }


    // 删除评论
    @DeleteMapping("/delete/{id}")
    public BaseResponse<Void> deleteReview(@PathVariable Long id) {
        boolean removeResult = reviewService.removeById(id);
        if (!removeResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "评论删除失败");
        }


        return ResultUtils.success(null);
    }
}
