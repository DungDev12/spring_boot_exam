package com.example.srs.validations;

import com.example.srs.enums.ERRORCODE;
import com.example.srs.enums.StatusCourses;
import com.example.srs.exceptions.AlreadyExistsException;
import com.example.srs.exceptions.BadRequestException;
import com.example.srs.exceptions.ResourceNotFoundException;
import com.example.srs.models.entities.Course;
import com.example.srs.models.entities.Enrollment;
import com.example.srs.models.entities.LessonProgress;
import com.example.srs.models.entities.User;
import com.example.srs.repositories.EnrollmentRepository;
import com.example.srs.repositories.LessonProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnrollmentValidator {

    private final EnrollmentRepository enrollmentRepository;
    private final LessonProgressRepository lessonProgressRepository;

    public void validateForCreate(Course course, User student){
        validateEnrollNotPublish(course);
        validateAlreadyEnroll(course.getId(),student.getId());
    }

    public LessonProgress validateForUpdateCompleteLesson(Enrollment enrollment, Long lessonId){
        validateEnrollNotPublish(enrollment.getCourse());

        LessonProgress lessonProgress =
                lessonProgressRepository
                        .findByEnrollment_IdAndLesson_Id(
                                enrollment.getId(),
                                lessonId
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Không tìm thấy bài học trong khóa học này",
                                        ERRORCODE.LESSON_PROGRESS_NOTFOUND
                                )
                        );

        if(lessonProgress.isCompleted()){
            throw new BadRequestException("Bài học này đã hoàn thành",ERRORCODE.UNAUTHORIZED);
        }

        return lessonProgress;
    }

    public void validateEnrollNotPublish(Course course){
        if(!course.getStatus().equals(StatusCourses.PUBLISHED)){
            throw new BadRequestException(
                    "Khóa học hiện chưa được phép đăng ký",
                    ERRORCODE.COURSE_NOT_AVAILABLE
            );
        }
    }

    public void validateAlreadyEnroll(Long courseId, Long studentId){
        if (enrollmentRepository.existsByStudent_IdAndCourse_Id(
                studentId,
                courseId
        )) {
            throw new AlreadyExistsException("Khoá học đã đăng ký trước đó", ERRORCODE.STUDENT_ENROLL_ALREADY_EXISTS);
        }
    }
}
