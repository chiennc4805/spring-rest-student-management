package com.myproject.sm.controller;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.sm.domain.TeacherAttendance;
import com.myproject.sm.domain.dto.request.ReqCreateAttendance;
import com.myproject.sm.domain.dto.request.ReqUpdateStudentAttendance;
import com.myproject.sm.domain.dto.response.ResultPaginationDTO;
import com.myproject.sm.service.TeacherAttendanceService;
import com.turkraft.springfilter.boot.Filter;

@RestController
public class TeacherAttendanceController {

    private final TeacherAttendanceService teacherAttendanceService;

    public TeacherAttendanceController(TeacherAttendanceService teacherAttendanceService) {
        this.teacherAttendanceService = teacherAttendanceService;
    }

    @PostMapping("/teacher-attendance")
    public ResponseEntity<TeacherAttendance> createTeacherAttendance(
            @RequestBody ReqCreateAttendance reqAttendance) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.teacherAttendanceService.handleCreateTeacherAttendance(reqAttendance));
    }

    @GetMapping("/teacher-attendance")
    public ResponseEntity<ResultPaginationDTO> fetchAllTeacherAttendances(
            @Filter Specification<TeacherAttendance> spec) {

        return ResponseEntity.ok(this.teacherAttendanceService.handleFetchAllTeacherAttendances(spec));
    }

    @PutMapping("teacher-attendance")
    public ResponseEntity<TeacherAttendance> updateTeacherAttendance(
            @RequestBody ReqUpdateStudentAttendance teacherAttendance) {

        return ResponseEntity.ok(this.teacherAttendanceService.handleUpdateTeacherAttendance(teacherAttendance));
    }

}
