package com.xye8.roc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xye8.roc.common.ErrorCode;
import com.xye8.roc.exception.BusinessException;
import com.xye8.roc.mapper.CourseMapper;
import com.xye8.roc.mapper.ReviewMapper;
import com.xye8.roc.mapper.SemesterMapper;
import com.xye8.roc.mapper.UserMapper;
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
    private ReviewMapper reviewMapper;

    @Resource
    private UserMapper userMapper; // 用于检查用户是否存在
    @Resource
    private CourseMapper courseMapper; // 用于检查课程是否存在
    @Resource
    private SemesterMapper semesterMapper; // 用于检查学期是否存在

    @Override
    public Review createReview(ReviewAddRequest reviewAddRequest) {
        //todo:检查是否是用户自己发送

        // 检查 user_id 是否存在
        // 检查 user_id 是否存在
        if (userMapper.selectById(reviewAddRequest.getUser_id()) == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "用户不存在");
        }

        // 检查 course_id 是否存在
        if (courseMapper.selectById(reviewAddRequest.getCourse_id()) == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "课程不存在");
        }


        // 创建新的 Review 实体对象
        Review review = new Review();
        // 将请求中的数据复制到实体对象中
        BeanUtils.copyProperties(reviewAddRequest, review);

        // 执行插入操作
        int rows = reviewMapper.insert(review);
        if (rows <= 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "评论创建失败");
        }

        return review;
    }

    @Override
    public Review updateReview(Long id, ReviewAddRequest reviewAddRequest) {

        // 查找现有的 Review
        Review existingReview = reviewMapper.selectById(id);
        if (existingReview == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "评论不存在");
        }


        // 检查 user_id 是否存在
        if (userMapper.selectById(reviewAddRequest.getUser_id()) == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "用户不存在");
        }

        // 检查 course_id 是否存在
        if (courseMapper.selectById(reviewAddRequest.getCourse_id()) == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "课程不存在");
        }


        // 将请求中的数据复制到现有的 Review 对象中
        BeanUtils.copyProperties(reviewAddRequest, existingReview);

        // 执行更新操作
        int rows = reviewMapper.updateById(existingReview);
        if (rows <= 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "评论更新失败");
        }

        return existingReview;
    }


}




