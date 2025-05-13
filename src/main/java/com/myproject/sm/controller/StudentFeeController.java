package com.myproject.sm.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.sm.domain.StudentFee;
import com.myproject.sm.domain.dto.request.ReqCreateStudentFee;
import com.myproject.sm.domain.dto.response.ResultPaginationDTO;
import com.myproject.sm.service.StudentFeeService;
import com.myproject.sm.service.exporter.StudentFeeExcelExporter;
import com.turkraft.springfilter.boot.Filter;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class StudentFeeController {

    private final StudentFeeService studentFeeService;

    public StudentFeeController(StudentFeeService studentFeeService) {
        this.studentFeeService = studentFeeService;
    }

    @PostMapping("/student-fee")
    public ResponseEntity<List<StudentFee>> createStudentFee(@RequestBody ReqCreateStudentFee reqStudentFee) {

        return ResponseEntity.status(HttpStatus.CREATED).body(this.studentFeeService.handleCreateStudentFee(
                reqStudentFee));
    }

    @GetMapping("/student-fee")
    public ResponseEntity<ResultPaginationDTO> fetchAllStudentFees(
            @Filter Specification<StudentFee> spec,
            Pageable pageable) {

        return ResponseEntity.ok(this.studentFeeService.handleFetchAllStudentFees(spec, pageable));
    }

    @GetMapping("/student-fee/export/excel")
    public void exportStudentFeeToExcel(@RequestParam("month") int month, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=hoc_phi_thang_" + month + ".xlsx";
        response.setHeader(headerKey, headerValue);

        Specification<StudentFee> spec = (root, query, builder) -> builder.equal(root.get("month"), month);
        List<StudentFee> studentFees = this.studentFeeService.handleFetchAllStudentFees(spec);

        StudentFeeExcelExporter excelExporter = new StudentFeeExcelExporter(studentFees, month);

        excelExporter.export(response);
    }

}
