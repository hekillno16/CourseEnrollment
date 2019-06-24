package com.rivers.microserviceusermanagement.repository;

import com.rivers.microserviceusermanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    @Query("SELECT u.name from User u where u.id in (:pIdList)")
    List<String> findUsernamesByIds(@Param("pIdList") List<Long> idList);
}
