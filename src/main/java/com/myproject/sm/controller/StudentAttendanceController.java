package com.myproject.sm.controller;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.sm.domain.StudentAttendance;
import com.myproject.sm.domain.dto.request.ReqCreateStudentAttendance;
import com.myproject.sm.domain.dto.request.ReqUpdateStudentAttendance;
import com.myproject.sm.domain.dto.response.ResultPaginationDTO;
import com.myproject.sm.service.StudentAttendanceService;
import com.turkraft.springfilter.boot.Filter;

@RestController
public class StudentAttendanceController {

    private StudentAttendanceService studentAttendanceService;

    public StudentAttendanceController(StudentAttendanceService studentAttendanceService) {
        this.studentAttendanceService = studentAttendanceService;
    }

    @PostMapping("/student-attendance")
    public ResponseEntity<List<StudentAttendance>> createStudentAttendance(
            @RequestBody ReqCreateStudentAttendance reqAttendance) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.studentAttendanceService.handleCreateStudentAttendance(reqAttendance));
    }

    @GetMapping("/student-attendance")
    public ResponseEntity<ResultPaginationDTO> fetchAllStudentAttendances(
            @Filter Specification<StudentAttendance> spec) {

        return ResponseEntity.ok(this.studentAttendanceService.handleFetchAllStudentAttendances(spec));
    }

    @PutMapping("student-attendance")
    public ResponseEntity<List<StudentAttendance>> updateStudentAttendance(
            @RequestBody List<ReqUpdateStudentAttendance> studentAttendances) {

        return ResponseEntity.ok(this.studentAttendanceService.handleUpdateStudentAttendance(studentAttendances));
    }

}
