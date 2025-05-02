package com.advsoftware.EduFlow.service.impel;

import com.advsoftware.EduFlow.DTO.CourseRequest;
import com.advsoftware.EduFlow.models.Course;
import com.advsoftware.EduFlow.models.User;
import com.advsoftware.EduFlow.repo.CourseRepo;
import com.advsoftware.EduFlow.repo.UserRepo;
import com.advsoftware.EduFlow.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImepl implements CourseService {
    @Autowired
    CourseRepo courseRepo;
    @Autowired
    UserRepo userRepository;


    @Override
    public void addCourse(CourseRequest request) {
        User instructor = userRepository.findById(request.getInstructorId())
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        // Optional: Check role
        boolean isInstructor = instructor.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_INSTRUCTOR"));
        if (!isInstructor) {
            throw new RuntimeException("User is not an instructor");
        }

        Course course = new Course();
        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setPrice(request.getPrice());
        course.setInstructor(instructor);

        courseRepo.save(course);
    }

    @Override
    public void updateCourse(Course course) {
        courseRepo.save(course);

    }

    @Override
    public void deleteCourseById(int id) {
        courseRepo.deleteById(id);

    }

    @Override
    public Course getCourse(int id) {
        return courseRepo.findById(id).get();
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepo.selectAllCourses();
    }


}
