package com.rivers.microservicelogmanagement.repository;

import com.rivers.microservicelogmanagement.model.Log;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface LogRepository extends CrudRepository<Log, UUID> {
}
