package com.myproject.sm.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.sm.domain.Schedule;
import com.myproject.sm.domain.dto.response.ResScheduleDTO;
import com.myproject.sm.service.ScheduleService;
import com.myproject.sm.util.error.IdInvalidException;

import jakarta.validation.Valid;

@RestController
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/schedule")
    public ResponseEntity<Schedule> createSchedule(@Valid @RequestBody Schedule reqSchedule) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.scheduleService.handleCreateAndUpdateSchedule(reqSchedule));
    }

    @GetMapping("/schedule")
    public ResponseEntity<List<ResScheduleDTO>> fetchScheduleInWeek(@RequestParam("startDate") LocalDate startDate) {

        return ResponseEntity.ok(this.scheduleService.handleFetchScheduleInWeek(startDate));
    }

    @PutMapping("/schedule")
    public ResponseEntity<Schedule> putCampus(@RequestBody Schedule reqSchedule) throws IdInvalidException {
        // check id exist
        Schedule scheduleDB = this.scheduleService.handleFetchScheduleById(reqSchedule.getId());
        if (scheduleDB == null) {
            throw new IdInvalidException("Schedule with id = " + reqSchedule.getId() + " không tồn tại");
        }

        // update
        Schedule updatedSchedule = this.scheduleService.handleCreateAndUpdateSchedule(reqSchedule);

        return ResponseEntity.ok(updatedSchedule);
    }

    @DeleteMapping("/schedule/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable("id") String id) throws IdInvalidException {
        // check id exist
        Schedule scheduleDB = this.scheduleService.handleFetchScheduleById(id);
        if (scheduleDB == null) {
            throw new IdInvalidException("Schedule with id = " + id + " không tồn tại");
        }

        // delete
        this.scheduleService.handleDeleteSchedule(id);
        return ResponseEntity.ok(null);
    }

}
