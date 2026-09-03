package com.example.srs.models.mapper;

import com.example.srs.models.entities.Course;
import com.example.srs.models.entities.Role;
import com.example.srs.models.entities.dto.request.course.CreateCourseRequest;
import com.example.srs.models.entities.dto.request.course.UpdateCourseRequest;
import com.example.srs.models.entities.dto.response.course.CourseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(target = "teacher", source = "teacher")
    CourseResponse toResponse(Course course);
    default String map(Role role) {
        return role == null ? null : role.getName();
    }

    Course toCourse(CreateCourseRequest dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    @Mapping(target = "enrollments", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    void updateCourseFromDto(
            UpdateCourseRequest dto,
            @MappingTarget Course course
    );
}
