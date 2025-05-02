package com.advsoftware.EduFlow.controllers;

import com.advsoftware.EduFlow.DTO.EnrollmentRequest;
import com.advsoftware.EduFlow.models.Enrollment;
import com.advsoftware.EduFlow.service.impel.EnrollmentServiceImpel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnrollmentController {

    @Autowired
    EnrollmentServiceImpel enrollmentService;

    @PostMapping("/enroll")
    public ResponseEntity<?> enroll(@RequestBody EnrollmentRequest request) {
        enrollmentService.enrollStudent(request);
        return ResponseEntity.ok().build();
    }
}

