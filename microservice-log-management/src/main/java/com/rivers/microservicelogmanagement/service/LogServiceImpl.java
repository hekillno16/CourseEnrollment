package com.rivers.microservicelogmanagement.service;

import com.rivers.microservicelogmanagement.model.Log;
import com.rivers.microservicelogmanagement.model.Summary;
import com.rivers.microservicelogmanagement.repository.LogRepository;
import com.rivers.microservicelogmanagement.repository.SummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private SummaryRepository summaryRepository;

    @Override
    public Log saveOrUpdate(Log log) {
        Summary existSummary =
                summaryRepository.findByCourseId(log.getCourseId()).orElse(null);

        if (existSummary != null) {
            //Cassandra can only update NON primary key value,
            //for prim val, first del then set
            summaryRepository.delete(existSummary);
            existSummary.setHitCount((existSummary.getHitCount() + 1));
            summaryRepository.save(existSummary);

        } else {
            Summary summary = new Summary();
            summary.setCourseId(log.getCourseId());
            summary.setHitCount((1L));
            summaryRepository.save(summary);
        }

        log.setId(UUID.randomUUID());
        logRepository.save(log);
        return log;
    }

    @Override
    public List<Summary> findPopularCourse() {
        return summaryRepository.findPopularCourses();
    }

    @Override
    public Summary findSummaryByCourseId(Long courseId) {
        return summaryRepository.findByCourseId(courseId).orElse(null);
    }

}
