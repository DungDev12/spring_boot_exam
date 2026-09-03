package com.example.srs.models.mapper;

import com.example.srs.models.entities.Enrollment;
import com.example.srs.models.entities.Role;
import com.example.srs.models.entities.dto.response.enrollment.DetailEnrollmentResponse;
import com.example.srs.models.entities.dto.response.enrollment.EnrollmentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {
                CourseMapper.class,
                LessonProgressMapper.class
        })
public interface EnrollmentMapper {

    EnrollmentResponse toResponse(Enrollment enrollment);

    DetailEnrollmentResponse toDetailResponse(Enrollment enrollment);
    default String map(Role role) {
        return role != null ? role.getName() : null;
    }
}
