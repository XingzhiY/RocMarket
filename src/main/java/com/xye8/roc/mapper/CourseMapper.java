package com.xye8.roc.mapper;

import com.xye8.roc.model.domain.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author Xingzhi Ye
* @description 针对表【course】的数据库操作Mapper
* @createDate 2024-08-12 17:59:22
* @Entity generator.domain.Course
*/
public interface CourseMapper extends BaseMapper<Course> {

    int countByCourseCodeAndProfessor(@Param("courseCode") String courseCode, @Param("professorId") Integer professorId);
}




