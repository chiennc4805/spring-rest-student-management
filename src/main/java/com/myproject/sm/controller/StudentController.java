package com.myproject.sm.controller;

import org.springframework.web.bind.annotation.RestController;

import com.myproject.sm.config.error.IdInvalidException;
import com.myproject.sm.domain.Student;
import com.myproject.sm.domain.response.ResultPaginationDTO;
import com.myproject.sm.service.StudentService;
import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student reqStudent) {
        Student newStudent = this.studentService.handleCreateStudent(reqStudent);

        return ResponseEntity.status(HttpStatus.CREATED).body(newStudent);
    }

    @GetMapping("/students")
    public ResponseEntity<ResultPaginationDTO> fetchAllStudents(
            @Filter Specification<Student> spec,
            Pageable pageable) {

        return ResponseEntity.ok(this.studentService.fetchAllStudents(spec, pageable));
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> fetchStudentById(@PathVariable("id") String id) throws IdInvalidException {
        Student student = this.studentService.fetchStudentById(id);
        if (student == null) {
            throw new IdInvalidException("Student with id = " + id + " không tồn tại");
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping("/students")
    public ResponseEntity<Student> updateStudent(@RequestBody Student reqStudent) throws IdInvalidException {
        Student studentDB = this.studentService.fetchStudentById(reqStudent.getId());
        if (studentDB == null) {
            throw new IdInvalidException("Student with id = " + reqStudent.getId() + " không tồn tại");
        }
        Student updatedStudent = this.studentService.updateStudent(reqStudent);

        return ResponseEntity.ok(updatedStudent);
    }

}
