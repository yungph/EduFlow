package com.advsoftware.EduFlow.service.impel;

import com.advsoftware.EduFlow.DTO.EnrollmentRequest;
import com.advsoftware.EduFlow.models.Course;
import com.advsoftware.EduFlow.models.Enrollment;
import com.advsoftware.EduFlow.models.User;
import com.advsoftware.EduFlow.repo.CourseRepo;
import com.advsoftware.EduFlow.repo.EnrollmentRepo;
import com.advsoftware.EduFlow.repo.UserRepo;
import com.advsoftware.EduFlow.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentServiceImpel implements EnrollmentService {
    @Autowired
    EnrollmentRepo repo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    CourseRepo courseRepo;

    @Override
    public void enrollStudent(EnrollmentRequest request) {
        User student = userRepo.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        boolean isStudent = student.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_STUDENT"));
        if (!isStudent) {
            throw new RuntimeException("User is not a student");
        }

        Course course = courseRepo.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        repo.save(enrollment);
    }

}
