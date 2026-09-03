package com.example.srs.validations;

import com.example.srs.enums.ERRORCODE;
import com.example.srs.exceptions.AccessDeniedException;
import com.example.srs.models.entities.Course;
import com.example.srs.models.entities.User;
import com.example.srs.models.entities.dto.request.course.CreateCourseRequest;
import com.example.srs.models.entities.dto.request.course.UpdateCourseRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CourseValidator {

    public void validateForCreate(User teacher, CreateCourseRequest request){
        validateTeacher(teacher);
    }

    public void validateForUpdate(Course course, User teacher, UpdateCourseRequest request){
        if(!Objects.equals(course.getTeacher().getId(), request.teacherId())){
            validateTeacher(teacher);
        }
    }

    public void validateTeacher(User user) {

        boolean isTeacher = user.getRoles()
                .stream()
                .anyMatch(role ->
                        "TEACHER".equals(role.getName())
                );

        if (!isTeacher) {
            throw new AccessDeniedException(
                    "User này không có vai trò TEACHER",
                    ERRORCODE.FORBIDDEN
            );
        }
    }
}
