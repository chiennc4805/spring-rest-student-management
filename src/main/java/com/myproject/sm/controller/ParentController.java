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

import com.myproject.sm.domain.Parent;
import com.myproject.sm.domain.Student;
import com.myproject.sm.domain.response.ResultPaginationDTO;
import com.myproject.sm.service.ParentService;
import com.myproject.sm.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;

@RestController
public class ParentController {

    private final ParentService parentService;

    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    @PostMapping("/parents")
    public ResponseEntity<Parent> createStudent(@Valid @RequestBody Parent reqStudent) {
        Parent newStudent = this.parentService.handleCreateParent(reqStudent);

        return ResponseEntity.status(HttpStatus.CREATED).body(newStudent);
    }

    @GetMapping("/parents")
    public ResponseEntity<ResultPaginationDTO> fetchAllStudents(
            @Filter Specification<Parent> spec,
            Pageable pageable) {

        return ResponseEntity.ok(this.parentService.fetchAllParents(spec, pageable));
    }

    @GetMapping("/parents/{id}")
    public ResponseEntity<Parent> fetchStudentById(@PathVariable("id") String id) throws IdInvalidException {
        Parent student = this.parentService.fetchParentById(id);
        if (student == null) {
            throw new IdInvalidException("Parent with id = " + id + " không tồn tại");
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping("/parents")
    public ResponseEntity<Parent> updateStudent(@RequestBody Parent reqParent) throws IdInvalidException {
        Parent parentDB = this.parentService.fetchParentById(reqParent.getId());
        if (parentDB == null) {
            throw new IdInvalidException("Parent with id = " + reqParent.getId() + " không tồn tại");
        }
        Parent updatedParent = this.parentService.updateParent(reqParent);

        return ResponseEntity.ok(updatedParent);
    }

    @DeleteMapping("/parents/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") String id) throws IdInvalidException {
        Parent parentDB = this.parentService.fetchParentById(id);
        if (parentDB == null) {
            throw new IdInvalidException("Parent with id = " + id + " không tồn tại");
        }
        this.parentService.deleteParent(id);
        return ResponseEntity.ok(null);
    }

}
