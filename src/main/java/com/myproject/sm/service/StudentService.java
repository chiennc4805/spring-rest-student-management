package com.myproject.sm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.myproject.sm.domain.Student;
import com.myproject.sm.domain.response.ResultPaginationDTO;
import com.myproject.sm.domain.response.ResultPaginationDTO.Meta;
import com.myproject.sm.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student handleCreateStudent(Student student) {
        return this.studentRepository.save(student);
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
        if (reqStudent.getName() != null) {
            studentDB.setName(reqStudent.getName());
        }
        if (reqStudent.getGender() != null) {
            studentDB.setGender(reqStudent.getGender());
        }
        if (reqStudent.getBirthDate() != null) {
            studentDB.setBirthDate(reqStudent.getBirthDate());
        }
        return this.studentRepository.save(studentDB);
    }

}
