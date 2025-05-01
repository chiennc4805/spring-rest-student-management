package com.myproject.sm.controller;

import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.sm.domain.Class;
import com.myproject.sm.domain.dto.response.ResultPaginationDTO;
import com.myproject.sm.service.ClassService;
import com.myproject.sm.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;

@RestController
public class ClassController {

    private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @PostMapping("/classes")
    public ResponseEntity<Class> createClass(@RequestBody Class reqClass) throws IdInvalidException {
        if (this.classService.isExistByName(reqClass.getName())) {
            throw new IdInvalidException("Lớp học với tên " + reqClass.getName() + " đã tồn tại");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(this.classService.handleCreateClass(reqClass));
    }

    @GetMapping("/classes")
    public ResponseEntity<ResultPaginationDTO> fetchAllClasses(
            @Filter Specification<Class> spec,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        if (page == null && size == null) {
            return ResponseEntity.ok(this.classService.handleFetchAllClasses());
        } else {
            Pageable pageable = PageRequest.of(page - 1, size);
            return ResponseEntity.ok(this.classService.handleFetchAllClasses(spec, pageable));
        }
    }

    @GetMapping("/classes/{id}")
    public ResponseEntity<Class> fetchStudentById(@PathVariable("id") String id) throws IdInvalidException {
        Class classDB = this.classService.handleFetchClassById(id);
        if (classDB == null) {
            throw new IdInvalidException("Class with id = " + id + " không tồn tại");
        }
        return ResponseEntity.ok(classDB);
    }

    @PutMapping("/classes")
    public ResponseEntity<Class> updateStudent(@RequestBody Class reqClass) throws IdInvalidException {
        Class classDB = this.classService.handleFetchClassById(reqClass.getId());
        if (classDB == null) {
            throw new IdInvalidException("Class with id = " + reqClass.getId() + " không tồn tại");
        }
        if (!reqClass.getName().equals(classDB.getName()) && this.classService.isExistByName(reqClass.getName())) {
            throw new IdInvalidException("Lớp học với tên " + reqClass.getName() + " đã tồn tại");
        }

        Class updatedStudent = this.classService.handleUpdateClass(reqClass);

        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/classes/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") String id) throws IdInvalidException {
        Class classDB = this.classService.handleFetchClassById(id);
        if (classDB == null) {
            throw new IdInvalidException("Class with id = " + id + " không tồn tại");
        }
        this.classService.handleDeleteClass(id);
        return ResponseEntity.ok(null);
    }

}
