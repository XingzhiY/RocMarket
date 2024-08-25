package com.xye8.roc.model.domain.original;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
public class Schedule {
    private String day;
    private String location;
    private String startTime;
    private String endTime;
    private String startDate;
    private String endDate;

    // Getters and setters for all fields

    @Override
    public String toString() {
        return "Schedule{" +
                "day='" + day + '\'' +
                ", location='" + location + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
