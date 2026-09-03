package com.example.srs.models.mapper;

import com.example.srs.models.entities.Lesson;
import com.example.srs.models.entities.Role;
import com.example.srs.models.entities.dto.request.lesson.CreateLessonRequest;
import com.example.srs.models.entities.dto.request.lesson.UpdateLessonRequest;
import com.example.srs.models.entities.dto.response.lesson.LessonInfoResponse;
import com.example.srs.models.entities.dto.response.lesson.LessonResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    Lesson toLesson(CreateLessonRequest request);
    LessonResponse toLessonResponse(Lesson lesson);

    LessonInfoResponse toLessonInfoResponse(Lesson lesson);
    default String map(Role role) {
        return role != null ? role.getName() : null;
    }

    @Mapping(target = "id", ignore = true)
    void updateLessonFromDto(
            UpdateLessonRequest dto,
            @MappingTarget Lesson lesson
    );
}
