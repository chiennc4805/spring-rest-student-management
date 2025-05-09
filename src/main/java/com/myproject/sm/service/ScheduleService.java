package com.myproject.sm.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.myproject.sm.domain.Class;
import com.myproject.sm.domain.Schedule;
import com.myproject.sm.domain.dto.response.ResultPaginationDTO;
import com.myproject.sm.domain.dto.response.ResultPaginationDTO.Meta;
import com.myproject.sm.repository.ScheduleRepository;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ClassService classService;

    public ScheduleService(ScheduleRepository scheduleRepository, ClassService classService) {
        this.scheduleRepository = scheduleRepository;
        this.classService = classService;
    }

    public Schedule handleCreateAndUpdateSchedule(Schedule reqSchedule) {
        if (reqSchedule.getClassInfo() != null) {
            Class classInfo = this.classService.handleFetchClassById(reqSchedule.getClassInfo().getId());
            reqSchedule.setClassInfo(classInfo);
        }
        return this.scheduleRepository.save(reqSchedule);
    }

    public void handleDeleteSchedule(String id) {
        this.scheduleRepository.deleteById(id);
    }

    public Schedule handleFetchScheduleById(String id) {
        Optional<Schedule> scheduleOptional = this.scheduleRepository.findById(id);
        return scheduleOptional.isPresent() ? scheduleOptional.get() : null;
    }

    public ResultPaginationDTO handleFetchAllSchedule(Specification<Schedule> spec, Pageable pageable) {
        Page<Schedule> pageSchedule = this.scheduleRepository.findAll(spec, pageable);
        ResultPaginationDTO res = new ResultPaginationDTO();
        Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageSchedule.getNumber() + 1);
        mt.setPageSize(pageSchedule.getSize());
        mt.setPages(pageSchedule.getTotalPages());
        mt.setTotal(pageSchedule.getTotalElements());
        res.setMeta(mt);
        res.setResult(pageSchedule.getContent());

        return res;
    }

}
