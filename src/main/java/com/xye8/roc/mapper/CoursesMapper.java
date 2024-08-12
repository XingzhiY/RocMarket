package com.xye8.roc.mapper;

import com.xye8.roc.model.domain.Courses;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Xingzhi Ye
 * @description 针对表【courses】的数据库操作Mapper
 * @createDate 2024-08-11 14:59:32
 * @Entity generator.domain.Courses
 */
public interface CoursesMapper extends BaseMapper<Courses> {

//    @Insert("INSERT INTO Courses (course_code, course_name, professor_id, credits) VALUES (#{course_code}, #{course_name}, #{professor_id}, #{credits})")
//    int insert(Courses course);

    /**
     * 获取表的所有记录总数
     * @param courseCode 表名
     * @return 记录总数
     */
    Integer countByCourseCode(@Param("courseCode") String courseCode);
}




