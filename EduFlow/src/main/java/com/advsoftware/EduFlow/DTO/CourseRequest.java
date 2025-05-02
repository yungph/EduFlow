package com.advsoftware.EduFlow.DTO;

import lombok.Data;

@Data
public class CourseRequest {
    private String name;
    private String description;
    private double price;
    private String instructorId; // user_id of instructor
}

