package com.myproject.sm.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.myproject.sm.domain.Class;
import com.myproject.sm.domain.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, String>, JpaSpecificationExecutor<Schedule> {

    @Query(value = "SELECT s FROM Schedule s JOIN s.classInfo c")
    List<Schedule> findByDateInWeek(LocalDate date);

    boolean existsBySlotNumberAndClassInfo(int slotNumber, Class classInfo);

}
