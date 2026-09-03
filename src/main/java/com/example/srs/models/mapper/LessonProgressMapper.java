package com.example.srs.models.mapper;

import com.example.srs.models.entities.LessonProgress;
import com.example.srs.models.entities.dto.response.lesson.progress.LessonProgressResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LessonProgressMapper {

    LessonProgressResponse toResponse(LessonProgress progress);

    List<LessonProgressResponse> toResponseList(
            List<LessonProgress> progresses
    );
}
