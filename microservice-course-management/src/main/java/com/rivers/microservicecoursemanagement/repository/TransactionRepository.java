package com.rivers.microservicecoursemanagement.repository;

import com.rivers.microservicecoursemanagement.model.Transaction;

import java.util.List;

public interface TransactionRepository extends IGenericDao<Transaction> {
    List<Transaction> findAllTransactionsOfUser(Long userId);

    List<Transaction> findAllTransactionsOfCourse(Long courseId);
}
