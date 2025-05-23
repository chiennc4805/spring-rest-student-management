package com.myproject.sm.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import com.myproject.sm.domain.Student;
import com.myproject.sm.domain.User;
import com.myproject.sm.domain.dto.StudentDTO;
import com.myproject.sm.domain.dto.response.ResultPaginationDTO;
import com.myproject.sm.service.ParentService;
import com.myproject.sm.service.StudentService;
import com.myproject.sm.service.UserService;
import com.myproject.sm.util.SecurityUtil;
import com.myproject.sm.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;
import com.turkraft.springfilter.builder.FilterBuilder;
import com.turkraft.springfilter.converter.FilterSpecificationConverter;

import jakarta.validation.Valid;

@RestController
public class StudentController {

    private final StudentService studentService;
    private final ParentService parentService;
    private final FilterBuilder filterBuilder;
    private final FilterSpecificationConverter filterSpecificationConverter;
    private final UserService userService;

    public StudentController(StudentService studentService, ParentService parentService, FilterBuilder filterBuilder,
            FilterSpecificationConverter filterSpecificationConverter, UserService userService) {
        this.studentService = studentService;
        this.parentService = parentService;
        this.filterBuilder = filterBuilder;
        this.filterSpecificationConverter = filterSpecificationConverter;
        this.userService = userService;
    }

    @PostMapping("/students")
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO reqStudentDTO)
            throws IdInvalidException {
        boolean isExistParent = this.parentService.isExistByTelephone(reqStudentDTO.getParent().getTelephone());
        if (!isExistParent) {
            throw new IdInvalidException(
                    "Phụ huynh với số điện thoại " + reqStudentDTO.getParent().getTelephone() + " không tồn tại");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(this.studentService.handleCreateStudent(reqStudentDTO));
    }

    @GetMapping("/students")
    public ResponseEntity<ResultPaginationDTO> fetchAllStudents(
            @Filter Specification<Student> spec,
            Pageable pageable) {

        Specification<Student> finalSpec = spec;
        String username = SecurityUtil.getCurrentUserLogin().isPresent() == true
                ? SecurityUtil.getCurrentUserLogin().get()
                : "";
        User currentUser = this.userService.handleGetUserByUsername(username);
        if (currentUser != null && currentUser.getRole().getName().equals("PARENT")) {
            List<String> studentIds = currentUser.getParentInfo().getStudents().stream().map(s -> s.getId())
                    .collect(Collectors.toList());
            if (!studentIds.isEmpty()) {
                Specification<Student> studentInSpec = (root, query, criteriaBuilder) -> root.get("id").in(studentIds);

                if (spec != null) {
                    finalSpec = Specification.where(studentInSpec).and(spec);
                } else {
                    finalSpec = studentInSpec;
                }
            }
        }

        return ResponseEntity.ok(this.studentService.handleFetchAllStudents(finalSpec, pageable));
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
        boolean isExistParent = this.parentService.isExistByTelephone(reqStudentDTO.getParent().getTelephone());
        if (!isExistParent) {
            throw new IdInvalidException(
                    "Phụ huynh với số điện thoại " + reqStudentDTO.getParent().getTelephone() + " không tồn tại");
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
