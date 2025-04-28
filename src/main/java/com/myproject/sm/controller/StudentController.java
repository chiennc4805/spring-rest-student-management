package com.myproject.sm.controller;

import org.springframework.web.bind.annotation.RestController;

import com.myproject.sm.domain.Student;
import com.myproject.sm.domain.dto.StudentDTO;
import com.myproject.sm.domain.dto.response.ResultPaginationDTO;
import com.myproject.sm.service.StudentService;
import com.myproject.sm.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO reqStudentDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.studentService.handleCreateStudent(reqStudentDTO));
    }

    @GetMapping("/students")
    public ResponseEntity<ResultPaginationDTO> fetchAllStudents(
            @Filter Specification<Student> spec,
            Pageable pageable) {

        return ResponseEntity.ok(this.studentService.handleFetchAllStudents(spec, pageable));
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<StudentDTO> handleFetchStudentById(@PathVariable("id") String id) throws IdInvalidException {
        Student student = this.studentService.findStudentById(id);
        if (student == null) {
            throw new IdInvalidException("Student with id = " + id + " không tồn tại");
        }

        return ResponseEntity.ok(this.studentService.convertStudentToStudentDTO(student));
    }

    @PutMapping("/students")
    public ResponseEntity<StudentDTO> updateStudent(@RequestBody StudentDTO reqStudentDTO) throws IdInvalidException {
        Student studentDB = this.studentService.findStudentById(reqStudentDTO.getId());
        if (studentDB == null) {
            throw new IdInvalidException("Student with id = " + reqStudentDTO.getId() + " không tồn tại");
        }

        return ResponseEntity.ok(this.studentService.handleUpdateStudent(reqStudentDTO));
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") String id) throws IdInvalidException {
        Student studentDB = this.studentService.findStudentById(id);
        if (studentDB == null) {
            throw new IdInvalidException("Student with id = " + id + " không tồn tại");
        }
        this.studentService.handleDeleteStudent(id);
        return ResponseEntity.ok(null);
    }

}
