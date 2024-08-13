package com.xye8.roc.service;

import com.xye8.roc.model.domain.Review;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xye8.roc.model.request.ReviewAddRequest;

/**
 * @author Xingzhi Ye
 * @description 针对表【reviews】的数据库操作Service
 * @createDate 2024-08-11 14:59:32
 */
public interface ReviewService extends IService<Review> {


//    List<Reviews> getAllReviews();

//    Reviews getReviewsById(Long id);

    Review createReview(ReviewAddRequest reviewAddRequest);

    Review updateReview(Long id, ReviewAddRequest reviewAddRequest);

    //    Reviews updateReviews(Long id, ReviewsUpdateRequest reviewUpdateRequest);
//
//    boolean deleteReviews(Long id);
    

}
