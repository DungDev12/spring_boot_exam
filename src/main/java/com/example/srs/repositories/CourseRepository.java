package com.example.srs.repositories;

import com.example.srs.models.entities.Course;
import com.example.srs.models.entities.dto.request.course.CourseFilterRequest;
import com.example.srs.models.entities.dto.response.report.TeacherCoursesOverviewResponse;
import com.example.srs.models.entities.dto.response.report.TopCourseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("""
    SELECT c
    FROM Course c
    WHERE (
        :#{#filter.search} IS NULL
        OR :#{#filter.search} = ''
        OR LOWER(c.title) LIKE LOWER(CONCAT('%', :#{#filter.search}, '%'))
        OR LOWER(c.description) LIKE LOWER(CONCAT('%', :#{#filter.search}, '%'))
    )
    AND (
        :#{#filter.teacherId} IS NULL
        OR c.teacher.id = :#{#filter.teacherId}
    )
    AND (
        :#{#filter.status} IS NULL
         OR c.status = :#{#filter.status}
        )
    """)
    Page<Course> findAllAndFilter(
            @Param("filter") CourseFilterRequest filter,
            Pageable pageable
    );

    boolean existsByIdAndTeacherId(
            Long courseId,
            Long teacherId
    );


    @Query("""
            SELECT new com.example.srs.models.entities.dto.response.report.TopCourseResponse(
                        c.id,
                        c.title,
                        COUNT(e.id)
                    )
            FROM Course c
            LEFT JOIN Enrollment e ON e.course.id = c.id
            GROUP BY c.id, c.title
            ORDER BY COUNT (e.id) DESC
       """)
    Page<TopCourseResponse> findTopCourse(Pageable pageable);


    @Query("""
        SELECT new com.example.srs.models.entities.dto.response.report.TeacherCoursesOverviewResponse(
                COUNT(DISTINCT c.id),
                COUNT(DISTINCT e.id),
                COUNT(DISTINCT e.student.id)
                )
        FROM Course c 
        LEFT JOIN Enrollment e ON e.course.id = c.id
        WHERE c.teacher.id = :teacherId
        """)
    TeacherCoursesOverviewResponse findTeacherCoursesOverview(
            @Param("teacherId") Long teacherId
    );
}
