package com.advsoftware.EduFlow.controllers;

import com.advsoftware.EduFlow.DTO.CourseRequest;
import com.advsoftware.EduFlow.models.Course;
import com.advsoftware.EduFlow.service.impel.CourseServiceImepl;
import jakarta.servlet.ServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    CourseServiceImepl courseService;

    @PostMapping()
    public ResponseEntity<?> addCourse(@RequestBody CourseRequest course) {
        courseService.addCourse(course);
        return ResponseEntity.ok().build();
    }

    @PutMapping()
    public ResponseEntity<?> updateCourse(@RequestBody Course course) {
        courseService.updateCourse(course);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourse(@PathVariable int id) {
        return ResponseEntity.ok().body(courseService.getCourse(id));
    }

    @GetMapping()
    public ResponseEntity<?> getAllCourses() {
        return ResponseEntity.ok().body(courseService.getAllCourses());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable int id) {
        courseService.deleteCourseById(id);
        return ResponseEntity.ok().build();
    }

}
