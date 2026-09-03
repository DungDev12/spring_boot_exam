package com.example.srs.services.impl;

import com.example.srs.enums.ERRORCODE;
import com.example.srs.enums.StatusCourses;
import com.example.srs.exceptions.ResourceNotFoundException;
import com.example.srs.models.entities.Course;
import com.example.srs.models.entities.User;
import com.example.srs.models.entities.dto.request.course.CourseFilterRequest;
import com.example.srs.models.entities.dto.request.course.CreateCourseRequest;
import com.example.srs.models.entities.dto.request.course.UpdateCourseRequest;
import com.example.srs.models.entities.dto.request.course.UpdateCourseStatusRequest;
import com.example.srs.models.entities.dto.response.course.CourseResponse;
import com.example.srs.models.mapper.CourseMapper;
import com.example.srs.repositories.CourseRepository;
import com.example.srs.securities.CurrentUserService;
import com.example.srs.securities.UserPrinciple;
import com.example.srs.services.CourseService;
import com.example.srs.validations.CourseValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseMapper courseMapper;
    private final CourseRepository courseRepository;
    private final UserServiceImpl userService;
    private final CurrentUserService currentUserService;
    private final CourseValidator courseValidator;

    @Override
    @Transactional
    public CourseResponse createCourse(CreateCourseRequest dto) {
        User teacher = userService.getById(dto.teacherId());
        courseValidator.validateForCreate(teacher, dto);
        Course newCourse = courseMapper.toCourse(dto);
        newCourse.setTeacher(teacher);
        return courseMapper.toResponse(courseRepository.save(newCourse));
    }

    @Override
    @Transactional
    public CourseResponse updateCourse(Long id, UpdateCourseRequest dto) {
        Course course = getById(id);
        if(dto.teacherId() != null){
            User teacher = userService.getById(dto.teacherId());
            courseValidator.validateForUpdate(course, teacher, dto);
            course.setTeacher(teacher);
        }
        courseMapper.updateCourseFromDto(dto, course);
        return courseMapper.toResponse(courseRepository.save(course));
    }

    @Override
    public CourseResponse updateCourseStatus(Long id, UpdateCourseStatusRequest dto) {
        Course course = getById(id);
        course.setStatus(dto.status());
        return courseMapper.toResponse(courseRepository.save(course));
    }


    @Override
    public Page<CourseResponse> getAll(CourseFilterRequest filter, Pageable pageable) {
        UserPrinciple user = currentUserService.getCurrentUser();
        boolean isAdmin = user.getAuthorities().stream().anyMatch(role ->
                Objects.equals(role.getAuthority(), "ROLE_ADMIN"));
        StatusCourses status = filter.status();
        if(!isAdmin){
            status = StatusCourses.PUBLISHED;
        }
        CourseFilterRequest newFilter =
                new CourseFilterRequest(
                        filter.search(),
                        filter.teacherId(),
                        status
                );
        return courseRepository.findAllAndFilter(newFilter,pageable)
                .map(courseMapper::toResponse);
    }

    @Override
    public Course getById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", id, ERRORCODE.COURSE_NOTFOUND));
    }

    @Override
    public void deleteCourseById(Long id) {
        Course course = getById(id);
        courseRepository.delete(course);
    }
}
