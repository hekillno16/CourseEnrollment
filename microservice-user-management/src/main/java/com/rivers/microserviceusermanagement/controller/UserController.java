package com.rivers.microserviceusermanagement.controller;

import com.rivers.microserviceusermanagement.model.Role;
import com.rivers.microserviceusermanagement.model.User;
import com.rivers.microserviceusermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/service/registration")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()) != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        user.setRole(Role.USER);
        userService.save(user);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/service/user")
    public ResponseEntity<?> getUser(Principal principal) {
        if (principal == null || principal.getName() == null) {
            return ResponseEntity.ok(principal);
        }

        return new ResponseEntity<User>(
          userService.findByUsername(principal.getName()),
          HttpStatus.OK
        );
    }

    @PostMapping("/service/names")
    public ResponseEntity getUsers(@RequestBody List<Long> idList) {
        return ResponseEntity.ok(userService.findUsernamesByIds(idList));
    }
}
