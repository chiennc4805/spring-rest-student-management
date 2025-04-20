package com.myproject.sm.controller;

import org.springframework.web.bind.annotation.RestController;

import com.myproject.sm.domain.Student;
import com.myproject.sm.service.StudentService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
        Student newStudent = this.studentService.handleCreateStudent(student);

        return ResponseEntity.status(HttpStatus.CREATED).body(newStudent);
    }

}
