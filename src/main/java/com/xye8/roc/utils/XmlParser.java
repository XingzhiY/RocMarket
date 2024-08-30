package com.xye8.roc.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xye8.roc.model.domain.Course;
import com.xye8.roc.model.domain.Professor;
import com.xye8.roc.model.domain.original.OriginalCourse;
import com.xye8.roc.model.domain.original.Schedule;
import com.xye8.roc.service.CourseService;
import com.xye8.roc.service.ProfessorService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import org.w3c.dom.*;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

@Component
public class XmlParser {

    @Resource
    ProfessorService professorService;
    @Resource
    CourseService courseService;

    public void parse(String xmlContent) {
        try {
//            System.out.println("-----------=======parse=========-----------------------");
            // 将字符串转换为输入流
            ByteArrayInputStream input = new ByteArrayInputStream(xmlContent.getBytes("UTF-8"));

            // 创建 DocumentBuilderFactory 并解析 XML 内容
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(input);
            doc.getDocumentElement().normalize();
            // 获取根元素 courses
            NodeList courseList = doc.getElementsByTagName("course");

            for (int temp = 0; temp < courseList.getLength(); temp++) {
                Node courseNode = courseList.item(temp);
                if (courseNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element courseElement = (Element) courseNode;
                    OriginalCourse original_course = new OriginalCourse();
                    //                    course.setCourseSectionListing(getTagValue("CourseSectionListing", courseElement));

                    String cnValue = getTagValue("cn", courseElement);//CSC131-1 = CSC131
                    original_course.setCn(processCourseCode(cnValue));

                    original_course.setTerm(getTagValue("term", courseElement));//Fall 2024 = fall2024
                    String termValue = getTagValue("term", courseElement);
                    original_course.setTerm(processTerm(termValue));

                    //                    course.setSchool(getTagValue("school", courseElement));
                    //                    course.setDept(getTagValue("dept", courseElement));
                    original_course.setTitle(getTagValue("title", courseElement).trim());//Introduction to Web

                    //                    course.setStatus(getTagValue("status", courseElement)); //Closed

                    String creditsStr = getTagValue("credits", courseElement).trim();
                    if (!isValidDouble(creditsStr)) { // 判断是不是有效的 double 类型
                        continue;
                    }
                    double credits = Double.parseDouble(creditsStr);
                    if (credits > 8) { // 判断转换后的 credits 是否大于 8
                        continue;
                    }
                    original_course.setCredits(creditsStr);

                    //                    course.setOffered(getTagValue("offered", courseElement));
                    //                    course.setDescription(getTagValue("description", courseElement));
                    //                    course.setRestrictions(getTagValue("restrictions", courseElement));

                    original_course.setInstructors(getTagValue("instructors", courseElement).trim());

                    //                    course.setSectionEnrolled(Integer.parseInt(getTagValue("sectionenrolled", courseElement))); //几个人选了
                    //                    course.setSectionCap(Integer.parseInt(getTagValue("sectioncap", courseElement)));//最大容量
                    //                    course.setUpdated(getTagValue("updated", courseElement));
                    // 处理 schedules 节点
                    //                    NodeList scheduleList = courseElement.getElementsByTagName("schedule");
                    //                    if (scheduleList.getLength() > 0) {
                    //                        Node scheduleNode = scheduleList.item(0);
                    //                        if (scheduleNode.getNodeType() == Node.ELEMENT_NODE) {
                    //                            Element scheduleElement = (Element) scheduleNode;
                    //                            Schedule schedule = new Schedule();
                    //                            schedule.setDay(getTagValue("day", scheduleElement));
                    //                            schedule.setLocation(getTagValue("location", scheduleElement));
                    //                            schedule.setStartTime(getTagValue("start_time", scheduleElement));
                    //                            schedule.setEndTime(getTagValue("end_time", scheduleElement));
                    //                            schedule.setStartDate(getTagValue("start_date", scheduleElement));
                    //                            schedule.setEndDate(getTagValue("end_date", scheduleElement));
                    //                            course.setSchedule(schedule);
                    //                        }
                    //                    }

                    // 打印解析后的 course 对象
//                    System.out.println(original_course);

                    // 过滤空 professor
                    if (original_course.getInstructors() == null || original_course.getInstructors().trim().isEmpty()) {
//                        System.out.println("Skipping course due to empty instructor name: " + original_course.getTitle());
                        continue; // Skip this course if the instructor name is empty
                    }

                    // 搞定professor id
                    Course course = mapFromOriginalCourse(original_course);
                    // 创建查询条件，查找教授的名字是否匹配
                    QueryWrapper<Professor> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("name", original_course.getInstructors());

                    // 获取与该名字匹配的教授对象
                    Professor professor = professorService.getOne(queryWrapper);

                    if (professor == null) {
                        professor = new Professor();
                        professor.setName(original_course.getInstructors());
                        // 你可以在这里设置教授的其他属性，例如教授的部门、职位等
                        professorService.save(professor);
                    }
                    course.setProfessor_id(professor.getId());

                    // 判断是否有重复的 course
                    QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
                    courseQueryWrapper.eq("course_code", course.getCourse_code()).eq("professor_id", course.getProfessor_id());

                    // 检查数据库中是否存在相同的课程
                    Course existingCourse = courseService.getOne(courseQueryWrapper);

                    if (existingCourse != null) {
                        // 如果找到了重复的课程，可以选择更新或者忽略
//                        System.out.println("Duplicate course found: " + existingCourse);
                        // 例如，你可以在这里执行更新操作，而不是插入
                        //TODO 给course添加term
                    } else {
                        // 如果没有找到重复的课程，保存新的课程
                        courseService.save(course);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Course mapFromOriginalCourse(OriginalCourse originalCourse) {
        Course course = new Course();

        // Assuming 'courseSectionListing' from OriginalCourse is equivalent to 'course_code' in Course
        course.setCourse_code(originalCourse.getCn());

        // Assuming 'title' from OriginalCourse is equivalent to 'course_name' in Course
        course.setCourse_name(originalCourse.getTitle());

        // Set credits, converting from double to integer if needed
        //        course.setCredits(Integer.parseInt(originalCourse.getCredits().trim()));
        int credits = (int) Double.parseDouble(originalCourse.getCredits().trim());
        course.setCredits(credits);

        return course;
    }

    public static String processTerm(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        // 去掉所有空格并转换为小写
        String processed = input.replaceAll("\\s+", "").toLowerCase();

        return processed;
    }

    public static String processCourseCode(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String processed = input.trim(); // 去掉前后空格
        processed = processed.toUpperCase(); // 转换为全大写
        int dashIndex = processed.indexOf("-"); // 找到'-'的索引

        if (dashIndex != -1) {
            processed = processed.substring(0, dashIndex); // 去掉'-'及之后的内容
        }

        return processed;
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList != null && nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            if (node != null) {
                return node.getTextContent();
            }
        }
        return "";
    }

    public static boolean isValidDouble(String creditString) {
        if (creditString == null || creditString.trim().isEmpty()) {
            return false;
        }

        return NumberUtils.isCreatable(creditString.trim());
    }
}
