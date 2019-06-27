package com.rivers.microservicelogmanagement.service;

import com.rivers.microservicelogmanagement.model.Log;
import com.rivers.microservicelogmanagement.model.Summary;

import java.util.List;

public interface LogService {
    Log saveOrUpdate(Log log);

    List<Summary> findPopularCourse();

    Summary findSummaryByCourseId(Long courseId);
}
