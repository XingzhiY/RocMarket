package com.xye8.roc.controller;

import com.xye8.roc.common.BaseResponse;
import com.xye8.roc.common.ErrorCode;
import com.xye8.roc.common.ResultUtils;
import com.xye8.roc.exception.BusinessException;
import com.xye8.roc.model.domain.Reviews;
import com.xye8.roc.model.request.ReviewAddRequest;
import com.xye8.roc.service.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewsController {

    @Resource
    private ReviewsService reviewsService;

    // 获取所有评论
//    @GetMapping
//    public BaseResponse<List<Review>> getAllReviews() {
//        List<Review> reviews = reviewsService.getAllReviews();
//        return ResultUtils.success(reviews);
//    }

    // 根据ID获取评论
//    @GetMapping("/{id}")
//    public BaseResponse<Review> getReviewById(@PathVariable Long id) {
//        Review review = reviewsService.getReviewById(id);
//        if (review == null) {
//            throw new BusinessException(ErrorCode.NOT_FOUND, "评论不存在");
//        }
//        return ResultUtils.success(review);
//    }

    // 创建评论
    @PostMapping
    public BaseResponse<Reviews> createReview(@RequestBody ReviewAddRequest reviewAddRequest) {
        if (reviewAddRequest == null ) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "reviewAddRequest = null");
        }
        Reviews review = reviewsService.createReviews(reviewAddRequest);
        return ResultUtils.success(review);
    }

    // 更新评论
//    @PutMapping("/update/{id}")
//    public BaseResponse<Review> updateReview(@PathVariable Long id, @RequestBody ReviewUpdateRequest reviewUpdateRequest) {
//        Review review = reviewsService.updateReview(id, reviewUpdateRequest);
//        return ResultUtils.success(review);
//    }
//
//    // 删除评论
//    @DeleteMapping("/delete/{id}")
//    public BaseResponse<Void> deleteReview(@PathVariable Long id) {
//        boolean result = reviewsService.deleteReview(id);
//        if (!result) {
//            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "删除评论失败");
//        }
//        return ResultUtils.success(null);
//    }
}
