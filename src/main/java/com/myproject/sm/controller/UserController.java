package com.myproject.sm.controller;

import org.springframework.web.bind.annotation.RestController;

import com.myproject.sm.config.error.IdInvalidException;
import com.myproject.sm.domain.Student;
import com.myproject.sm.domain.User;
import com.myproject.sm.domain.response.ResultPaginationDTO;
import com.myproject.sm.service.UserService;
import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User reqUser) {
        String hashPassword = this.passwordEncoder.encode(reqUser.getPassword());
        reqUser.setPassword(hashPassword);
        User newUser = this.userService.handleCreateUser(reqUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User reqUser) throws IdInvalidException {
        User userDB = this.userService.fetchUserById(reqUser.getId());
        if (userDB == null) {
            throw new IdInvalidException("User with id = " + reqUser.getId() + " không tồn tại");
        }
        User updatedUser = this.userService.handleUpdateUser(reqUser);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) throws IdInvalidException {
        User userDB = this.userService.fetchUserById(id);
        if (userDB == null) {
            throw new IdInvalidException("User with id = " + id + " không tồn tại");
        }
        this.userService.handleDeleteUser(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/users")
    public ResponseEntity<ResultPaginationDTO> fetchAllStudents(
            @Filter Specification<User> spec,
            Pageable pageable) {

        return ResponseEntity.ok(this.userService.fetchAllUsers(spec, pageable));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> fetchUserById(@PathVariable("id") String id) throws IdInvalidException {
        User user = this.userService.fetchUserById(id);
        if (user == null) {
            throw new IdInvalidException("User with id = " + id + " không tồn tại");
        }
        return ResponseEntity.ok(user);
    }

}
