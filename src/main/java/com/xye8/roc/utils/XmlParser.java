package com.xye8.roc.utils;

import com.xye8.roc.model.domain.original.OriginalCourse;
import com.xye8.roc.model.domain.original.Schedule;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

public class XmlParser {

    public static void parse(String xmlContent) {
        try {
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
                    
                    OriginalCourse course = new OriginalCourse();
                    course.setCourseSectionListing(getTagValue("CourseSectionListing", courseElement));
                    course.setCn(getTagValue("cn", courseElement));
                    course.setTerm(getTagValue("term", courseElement));
                    course.setSchool(getTagValue("school", courseElement));
                    course.setDept(getTagValue("dept", courseElement));
                    course.setTitle(getTagValue("title", courseElement));
                    course.setStatus(getTagValue("status", courseElement));
                    course.setCredits(getTagValue("credits", courseElement).trim());
                    course.setOffered(getTagValue("offered", courseElement));
                    course.setDescription(getTagValue("description", courseElement));
                    course.setRestrictions(getTagValue("restrictions", courseElement));
                    course.setInstructors(getTagValue("instructors", courseElement));
                    course.setSectionEnrolled(Integer.parseInt(getTagValue("sectionenrolled", courseElement)));
                    course.setSectionCap(Integer.parseInt(getTagValue("sectioncap", courseElement)));
                    course.setUpdated(getTagValue("updated", courseElement));
                    
                    // 处理 schedules 节点
                    NodeList scheduleList = courseElement.getElementsByTagName("schedule");
                    if (scheduleList.getLength() > 0) {
                        Node scheduleNode = scheduleList.item(0);
                        if (scheduleNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element scheduleElement = (Element) scheduleNode;
                            Schedule schedule = new Schedule();
                            schedule.setDay(getTagValue("day", scheduleElement));
                            schedule.setLocation(getTagValue("location", scheduleElement));
                            schedule.setStartTime(getTagValue("start_time", scheduleElement));
                            schedule.setEndTime(getTagValue("end_time", scheduleElement));
                            schedule.setStartDate(getTagValue("start_date", scheduleElement));
                            schedule.setEndDate(getTagValue("end_date", scheduleElement));
                            course.setSchedule(schedule);
                        }
                    }
                    
                    // 打印解析后的 course 对象
                    System.out.println(course);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
