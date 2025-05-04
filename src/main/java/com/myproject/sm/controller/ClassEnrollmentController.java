package com.myproject.sm.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.sm.domain.ClassEnrollment;
import com.myproject.sm.domain.dto.response.ResultPaginationDTO;
import com.myproject.sm.service.ClassEnrollmentService;
import com.turkraft.springfilter.boot.Filter;

@RestController
public class ClassEnrollmentController {

    private final ClassEnrollmentService classEnrollmentService;

    public ClassEnrollmentController(ClassEnrollmentService classEnrollmentService) {
        this.classEnrollmentService = classEnrollmentService;
    }

    @GetMapping("/class-enrollments")
    public ResponseEntity<ResultPaginationDTO> fetchAllSubjects(
            @Filter Specification<ClassEnrollment> spec,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        if (page == null && size == null) {
            return ResponseEntity.ok(this.classEnrollmentService.handleFetchAllClassEnrollments(spec));
        } else {
            Pageable pageable = PageRequest.of(page - 1, size);
            return ResponseEntity.ok(this.classEnrollmentService.handleFetchAllClassEnrollments(spec, pageable));
        }
    }

}
