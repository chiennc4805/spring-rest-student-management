package com.myproject.sm.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.myproject.sm.domain.Class;
import com.myproject.sm.domain.Student;
import com.myproject.sm.domain.StudentAttendance;

@Repository
public interface StudentAttendanceRepository
                extends JpaRepository<StudentAttendance, String>, JpaSpecificationExecutor<StudentAttendance> {

        List<StudentAttendance> findByDate(LocalDate date);

        boolean existsByClassInfoAndDateAndSlot(Class classInfo, LocalDate date, int slot);

        Optional<StudentAttendance> findByClassInfoAndSlotAndDateAndStudent(Class classInfo, int slot, LocalDate date,
                        Student student);

        @Query("SELECT sa FROM StudentAttendance sa WHERE sa.student = ?1 AND MONTH(sa.date) = ?2 ")
        List<StudentAttendance> findByStudentAndMonthOfDate(Student s, int month);
}
