package com.example.srs.models.entities.dto.response.enrollment;


import com.example.srs.enums.StatusEnrollments;
import com.example.srs.models.entities.dto.response.course.CourseResponse;
import com.example.srs.models.entities.dto.response.lesson.progress.LessonProgressResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record DetailEnrollmentResponse(
        Long id,
        StatusEnrollments status,
        BigDecimal progressPercentage,
        LocalDateTime enrollmentDate,
        LocalDateTime completionDate,
        CourseResponse course,
        List<LessonProgressResponse> lessonProgresses
) {
}
