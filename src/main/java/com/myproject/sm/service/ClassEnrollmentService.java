package com.myproject.sm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myproject.sm.domain.ClassEnrollment;
import com.myproject.sm.domain.Student;
import com.myproject.sm.repository.ClassEnrollmentRepository;
import com.myproject.sm.domain.Class;

@Service
public class ClassEnrollmentService {

    private final ClassEnrollmentRepository classEnrollmentRepository;

    public ClassEnrollmentService(ClassEnrollmentRepository classEnrollmentRepository) {
        this.classEnrollmentRepository = classEnrollmentRepository;
    }

    public ClassEnrollment handleCreateClassEnrollment(ClassEnrollment reqClassEnrollment) {
        return this.classEnrollmentRepository.save(reqClassEnrollment);
    }

    public ClassEnrollment handleUpdateClassEnrollment(ClassEnrollment reqClassEnrollment) {
        return this.classEnrollmentRepository.save(reqClassEnrollment);
    }

    public void deleteClassEnrollment(Object object) {
        List<ClassEnrollment> classEnrollments = null;
        if (object instanceof Class cl) {
            classEnrollments = this.classEnrollmentRepository.findByEnrollmentClass(cl);
        } else if (object instanceof Student student) {
            classEnrollments = this.classEnrollmentRepository.findByEnrollmentStudent(student);
        }
        if (classEnrollments != null) {
            classEnrollments.stream().forEach(c -> this.classEnrollmentRepository.delete(c));
        }
    }

}
