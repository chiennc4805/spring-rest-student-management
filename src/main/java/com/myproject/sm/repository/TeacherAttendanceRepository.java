package com.myproject.sm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.myproject.sm.domain.TeacherAttendance;

@Repository
public interface TeacherAttendanceRepository
        extends JpaRepository<TeacherAttendance, String>, JpaSpecificationExecutor<TeacherAttendance> {

}
