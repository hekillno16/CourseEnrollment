package com.rivers.microservicecoursemanagement.controller;

import com.rivers.microservicecoursemanagement.intercomm.LogClient;
import com.rivers.microservicecoursemanagement.intercomm.UserClient;
import com.rivers.microservicecoursemanagement.model.Transaction;
import com.rivers.microservicecoursemanagement.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/service")
public class CourseController {

    @Autowired
    private LogClient logClient;

    @Autowired
    private UserClient userClient;

    @Autowired
    private CourseService courseService;

    @PostMapping("/user")
    public ResponseEntity<?> filterTransactions(@RequestBody Long userId) {
        return new ResponseEntity<>(courseService.filterTransactionsOfUser(userId), HttpStatus.OK);
    }

    @GetMapping("/popular")
    public ResponseEntity<?> popularCourses() {
        List<Long> popularsIdList = logClient.getPopularCourses();

        if (popularsIdList != null || popularsIdList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(courseService.filterCoursesByIdList(popularsIdList));
    }

    @PostMapping("/")
    public ResponseEntity<?> allCourse() {
        return ResponseEntity.ok(courseService.allCourses());
    }

    @PostMapping("/filter")
    public ResponseEntity<?> filterCourses(@RequestBody String text) {
        return ResponseEntity.ok(courseService.filterCourses(text));
    }

    @PostMapping("/enroll")
    public ResponseEntity<?> saveTransaction(@RequestBody Transaction transaction) {
        transaction.setDateOfIssue(LocalDateTime.now());
        transaction.setCourse(courseService.findCourseById(transaction.getCourse().getId()));
        courseService.saveTransaction(transaction);

        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/students")
    public ResponseEntity<?> findCourseStudents(@RequestBody Long courseId) {
        List<Transaction> list = courseService.filterTransactionsOfCourse(courseId);

        if (list != null && !list.isEmpty()) {
            List<Long> userIdList = list.parallelStream().map(Transaction::getUserId).collect(Collectors.toList());
            List<String> students = userClient.getUsers(userIdList);
            return ResponseEntity.ok(students);
        }

        return ResponseEntity.notFound().build();
    }


}
