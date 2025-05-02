package com.advsoftware.EduFlow.repo;

import com.advsoftware.EduFlow.models.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepo extends JpaRepository<Enrollment, Integer> {
}
