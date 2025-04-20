// package com.myproject.sm.controller;

// import org.springframework.web.bind.annotation.RestController;

// import com.myproject.sm.domain.User;
// import com.myproject.sm.service.UserService;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;

// @RestController
// public class UserController {

// private final UserService userService;

// public UserController(UserService userService) {
// this.userService = userService;
// }

// @PostMapping("/users")
// public ResponseEntity<User> createUser(@RequestBody User reqUser) {
// User newUser = this.userService.handleCreateUser(reqUser);
// return ResponseEntity.ok(newUser);
// }

// }
