package com.myproject.sm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.myproject.sm.domain.Class;
import com.myproject.sm.domain.ClassEnrollment;
import com.myproject.sm.domain.Student;

import java.util.List;

@Repository
public interface ClassEnrollmentRepository
                extends JpaRepository<ClassEnrollment, String>, JpaSpecificationExecutor<ClassEnrollment> {

        List<ClassEnrollment> findByEnrollmentClass(Class enrollmentClass);

        List<ClassEnrollment> findByEnrollmentStudent(Student enrollmentStudent);
}
