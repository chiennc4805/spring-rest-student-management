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

import com.myproject.sm.domain.Subject;
import com.myproject.sm.domain.response.ResultPaginationDTO;
import com.myproject.sm.service.SubjectService;
import com.myproject.sm.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;

@RestController
public class SubjectController {

    private SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping("/subjects")
    public ResponseEntity<Subject> createSubject(@Valid @RequestBody Subject reqSubject) throws IdInvalidException {
        // check name exist
        boolean isExistByName = this.subjectService.isExistByName(reqSubject.getName());
        if (isExistByName) {
            throw new IdInvalidException("Subject với tên = " + reqSubject.getName() + " đã tồn tại");
        }

        // create
        Subject newSubject = this.subjectService.handleCreateSubject(reqSubject);

        return ResponseEntity.status(HttpStatus.CREATED).body(newSubject);
    }

    @GetMapping("/subjects")
    public ResponseEntity<ResultPaginationDTO> fetchAllSubjects(
            @Filter Specification<Subject> spec,
            Pageable pageable) {

        return ResponseEntity.ok(this.subjectService.fetchAllSubjects(spec, pageable));
    }

    @GetMapping("/subjects/{id}")
    public ResponseEntity<Subject> fetchSubjectById(@PathVariable("id") String id) throws IdInvalidException {
        // check id exist
        Subject subject = this.subjectService.fetchSubjectById(id);
        if (subject == null) {
            throw new IdInvalidException("Subject với id = " + id + " không tồn tại");
        }
        return ResponseEntity.ok(subject);
    }

    @PutMapping("/subjects")
    public ResponseEntity<Subject> updateSubject(@RequestBody Subject reqSubject) throws IdInvalidException {
        // check id exist
        Subject subjectDB = this.subjectService.fetchSubjectById(reqSubject.getId());
        if (subjectDB == null) {
            throw new IdInvalidException("Subject with id = " + reqSubject.getId() + " không tồn tại");
        }

        // check name exist
        boolean isExistByName = this.subjectService.isExistByName(reqSubject.getName());
        if (isExistByName && !reqSubject.getName().equals(subjectDB.getName())) {
            throw new IdInvalidException("Subject với tên = " + reqSubject.getName() + " đã tồn tại");
        }

        // update
        Subject updatedSubject = this.subjectService.updateSubject(reqSubject);

        return ResponseEntity.ok(updatedSubject);
    }

    @DeleteMapping("/subjects/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable("id") String id) throws IdInvalidException {
        // check id exist
        Subject subjectDB = this.subjectService.fetchSubjectById(id);
        if (subjectDB == null) {
            throw new IdInvalidException("Subject with id = " + id + " không tồn tại");
        }

        // delete
        this.subjectService.deleteSubject(id);
        return ResponseEntity.ok(null);
    }

}
