package com.myproject.sm.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.sm.domain.Schedule;
import com.myproject.sm.domain.dto.response.ResultPaginationDTO;
import com.myproject.sm.service.ClassService;
import com.myproject.sm.service.ScheduleService;
import com.myproject.sm.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;

@RestController
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ClassService classService;

    public ScheduleController(ScheduleService scheduleService, ClassService classService) {
        this.scheduleService = scheduleService;
        this.classService = classService;
    }

    @PostMapping("/schedule")
    public ResponseEntity<Schedule> createSchedule(@Valid @RequestBody Schedule reqSchedule) throws IdInvalidException {
        // Class classInfo =
        // this.classService.handleFetchClassById(reqSchedule.getClassInfo().getId());
        // if
        // (this.scheduleService.isExistBySlotNumberAndClass(reqSchedule.getSlotNumber(),
        // reqSchedule.getClassInfo())) {
        // throw new IdInvalidException("Class " + classInfo.getName() + " đã có lịch
        // chứa ca học "
        // + reqSchedule.getSlotNumber());
        // }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.scheduleService.handleCreateAndUpdateSchedule(reqSchedule));
    }

    @GetMapping("/schedule")
    public ResponseEntity<ResultPaginationDTO> fetchAllSchedule(
            @Filter Specification<Schedule> spec,
            Pageable pageable) {

        return ResponseEntity.ok(this.scheduleService.handleFetchAllSchedule(spec, pageable));
    }

    @PutMapping("/schedule")
    public ResponseEntity<Schedule> putCampus(@RequestBody Schedule reqSchedule) throws IdInvalidException {
        // check id exist
        Schedule scheduleDB = this.scheduleService.handleFetchScheduleById(reqSchedule.getId());
        if (scheduleDB == null) {
            throw new IdInvalidException("Schedule with id = " + reqSchedule.getId() + " không tồn tại");
        }
        // if
        // (this.scheduleService.isExistBySlotNumberAndClass(reqSchedule.getSlotNumber(),
        // reqSchedule.getClassInfo())
        // && reqSchedule.getSlotNumber() != reqSchedule.getSlotNumber()) {
        // throw new IdInvalidException("Class " + reqSchedule.getClassInfo().getName()
        // + " đã có lịch chứa ca học "
        // + reqSchedule.getSlotNumber());
        // }
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
