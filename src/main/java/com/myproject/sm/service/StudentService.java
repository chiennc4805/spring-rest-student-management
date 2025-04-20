package com.myproject.sm.service;

import org.springframework.stereotype.Service;

import com.myproject.sm.domain.Student;
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

}
