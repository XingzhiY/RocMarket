package com.xye8.roc.model.domain.original;

import lombok.Data;

@Data
public class OriginalCourse {
    private String courseSectionListing;
    private String cn;
    private String term;
    private String school;
    private String dept;
    private String title;
    private String status;
    private String credits;
    private String offered;
    private String description;
    private String restrictions;
    private String instructors;
    private int sectionEnrolled;
    private int sectionCap;
    private String updated;
    private Schedule schedule;

    // Getters and setters for all fields

    @Override
    public String toString() {
        return "OriginalCourse{" +
                "courseSectionListing='" + courseSectionListing + '\'' +
                ", cn='" + cn + '\'' +
                ", term='" + term + '\'' +
                ", school='" + school + '\'' +
                ", dept='" + dept + '\'' +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", credits=" + credits +
                ", offered='" + offered + '\'' +
                ", description='" + description + '\'' +
                ", restrictions='" + restrictions + '\'' +
                ", instructors='" + instructors + '\'' +
                ", sectionEnrolled=" + sectionEnrolled +
                ", sectionCap=" + sectionCap +
                ", updated='" + updated + '\'' +
                ", schedule=" + schedule +
                '}';
    }
}
