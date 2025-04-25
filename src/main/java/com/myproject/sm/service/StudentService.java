package com.myproject.sm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.myproject.sm.domain.Parent;
import com.myproject.sm.domain.Student;
import com.myproject.sm.domain.response.ResultPaginationDTO;
import com.myproject.sm.domain.response.ResultPaginationDTO.Meta;
import com.myproject.sm.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final ParentService parentService;

    public StudentService(StudentRepository studentRepository, ParentService parentService) {
        this.studentRepository = studentRepository;
        this.parentService = parentService;
    }

    public Student handleCreateStudent(Student reqStudent) {
        if (reqStudent.getParent() != null) {
            Parent parent = this.parentService.fetchParentById(reqStudent.getParent().getId());
            reqStudent.setParent(parent);
        }
        return this.studentRepository.save(reqStudent);
    }

    public ResultPaginationDTO fetchAllStudents(Specification<Student> spec, Pageable pageable) {
        Page<Student> pageStudent = this.studentRepository.findAll(spec, pageable);
        ResultPaginationDTO res = new ResultPaginationDTO();
        Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageStudent.getNumber() + 1);
        mt.setPageSize(pageStudent.getSize());
        mt.setPages(pageStudent.getTotalPages());
        mt.setTotal(pageStudent.getTotalElements());

        res.setMeta(mt);
        res.setResult(pageStudent.getContent());

        return res;
    }

    public Student fetchStudentById(String id) {
        Optional<Student> studentOptional = this.studentRepository.findById(id);
        Student s = studentOptional.isPresent() ? studentOptional.get() : null;
        return s;
    }

    public Student updateStudent(Student reqStudent) {
        Student studentDB = this.fetchStudentById(reqStudent.getId());
        // update student
        studentDB.setName(reqStudent.getName());
        studentDB.setGender(reqStudent.getGender());
        studentDB.setBirthDate(reqStudent.getBirthDate());
        if (reqStudent.getParent() != null) {
            Parent parent = this.parentService.fetchParentById(reqStudent.getParent().getId());
            studentDB.setParent(parent != null ? parent : null);
        }

        return this.studentRepository.save(studentDB);
    }

    public void deleteStudent(String id) {
        this.studentRepository.deleteById(id);
    }

}
