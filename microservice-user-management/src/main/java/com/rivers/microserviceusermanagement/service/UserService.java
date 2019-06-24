package com.rivers.microserviceusermanagement.service;

import com.rivers.microserviceusermanagement.model.User;

import java.util.List;

public interface UserService {
    void save(User user);

    User findByUsername(String username);

    List<String> findUsernamesByIds(List<Long> idList);
}
