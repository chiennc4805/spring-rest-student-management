package com.myproject.sm.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.myproject.sm.domain.StudentAttendance;

@Repository
public interface StudentAttendanceRepository
        extends JpaRepository<StudentAttendance, String>, JpaSpecificationExecutor<StudentAttendance> {

    List<StudentAttendance> findByDate(LocalDate date);

}
