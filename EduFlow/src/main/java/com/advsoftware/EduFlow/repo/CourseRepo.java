package com.advsoftware.EduFlow.repo;

import com.advsoftware.EduFlow.models.Course;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepo extends JpaRepository<Course, Integer> {
//    List<Course> findByInstructorId(int instructorId);

    @Query(value = "SELECT * FROM Course WHERE " +
            "(:name IS NULL OR name LIKE %:name%) AND " +
            "(:minPrice IS NULL OR price >= :minPrice)",nativeQuery = true)
    List<Course> searchCourses(@Param("name") String name,
                               @Param("minPrice") Double minPrice);

    // Popular courses (with enrollment count)
    @Query("SELECT c, COUNT(e) as enrollments FROM Course c LEFT JOIN c.enrollments e " +
            "GROUP BY c ORDER BY enrollments DESC")
    List<Object[]> findPopularCourses(Pageable pageable);

    @Query(value = "select * from course ",nativeQuery = true)
    List<Course> selectAllCourses();

}
