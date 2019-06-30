package com.rivers.microservicecoursemanagement.repository;

import com.rivers.microservicecoursemanagement.model.Course;

import java.util.List;

public interface CourseRepository extends IGenericDao<Course> {
    List<Course> filterCourses(final String text);

    List<Course> filterCoursesByIdList(final List<Long> idList);
}
