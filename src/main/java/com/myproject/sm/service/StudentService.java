package com.myproject.sm.service;

import java.util.List;
import java.util.Optional;

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

    public List<Student> fetchAllStudents() {
        return this.studentRepository.findAll();
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
