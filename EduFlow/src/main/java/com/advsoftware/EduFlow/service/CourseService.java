package com.advsoftware.EduFlow.service;

import com.advsoftware.EduFlow.DTO.CourseRequest;
import com.advsoftware.EduFlow.models.Course;

import java.util.List;

public interface CourseService {
    public void addCourse(CourseRequest course);
    public void updateCourse(Course course);
    public void deleteCourseById(int id);
    public Course getCourse(int id);
    public List<Course> getAllCourses();
}
