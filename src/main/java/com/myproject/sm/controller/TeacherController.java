package com.myproject.sm.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.sm.domain.Teacher;
import com.myproject.sm.domain.response.ResultPaginationDTO;
import com.myproject.sm.service.TeacherService;
import com.myproject.sm.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;

@RestController
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/teachers")
    public ResponseEntity<Teacher> createTeacher(@Valid @RequestBody Teacher reqTeacher) {
        Teacher newTeacher = this.teacherService.handleCreateTeacher(reqTeacher);

        return ResponseEntity.status(HttpStatus.CREATED).body(newTeacher);
    }

    @GetMapping("/teachers")
    public ResponseEntity<ResultPaginationDTO> fetchAllTeachers(
            @Filter Specification<Teacher> spec,
            Pageable pageable) {

        return ResponseEntity.ok(this.teacherService.handleFetchAllTeachers(spec, pageable));
    }

    @GetMapping("/teachers/{id}")
    public ResponseEntity<Teacher> fetchTeacherById(@PathVariable("id") String id) throws IdInvalidException {
        Teacher student = this.teacherService.handleFetchTeacherById(id);
        if (student == null) {
            throw new IdInvalidException("Teacher with id = " + id + " không tồn tại");
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping("/teachers")
    public ResponseEntity<Teacher> updateTeacher(@RequestBody Teacher reqTeacher) throws IdInvalidException {
        Teacher teacherDB = this.teacherService.handleFetchTeacherById(reqTeacher.getId());
        if (teacherDB == null) {
            throw new IdInvalidException("Teacher with id = " + reqTeacher.getId() + " không tồn tại");
        }
        Teacher updatedStudent = this.teacherService.handleUpdateTeacher(reqTeacher);

        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/teachers/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable("id") String id) throws IdInvalidException {
        Teacher teacherDB = this.teacherService.handleFetchTeacherById(id);
        if (teacherDB == null) {
            throw new IdInvalidException("Teacher with id = " + id + " không tồn tại");
        }
        this.teacherService.deleteTeacher(id);
        return ResponseEntity.ok(null);
    }

}
