package com.example.srs.permissions;

import com.example.srs.enums.ERRORCODE;
import com.example.srs.exceptions.AccessDeniedException;
import com.example.srs.repositories.CourseRepository;
import com.example.srs.repositories.LessonRepository;
import com.example.srs.securities.CurrentUserService;
import com.example.srs.securities.UserPrinciple;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CoursePermission {

    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final CurrentUserService currentUserService;

    public boolean isOwner(
            Long courseId
    ) {
        boolean isOwner =
                courseRepository.existsByIdAndTeacherId(
                        courseId,
                        currentUserService.getCurrentUserId()
                );

        if(!isOwner){
            throw new AccessDeniedException("Bạn không có quyền trong hạng mục này", ERRORCODE.FORBIDDEN);
        }

        return true;
    }

    public boolean isLessonOwner(
            Long lessonId
    ) {
        boolean isOwner =
                lessonRepository.existsByIdAndCourse_Teacher_Id(
                        lessonId,
                        currentUserService.getCurrentUserId()
                );

        if(!isOwner){
            throw new AccessDeniedException("Bạn không có quyền trong hạng mục này", ERRORCODE.FORBIDDEN);
        }
        return true;
    }
}
