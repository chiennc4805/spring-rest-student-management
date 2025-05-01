package com.myproject.sm.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.myproject.sm.domain.Class;
import com.myproject.sm.domain.Schedule;
import com.myproject.sm.domain.dto.response.ResScheduleDTO;
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

    public List<ResScheduleDTO> handleFetchScheduleInWeek(LocalDate startDate) {
        List<Schedule> schedules = this.scheduleRepository.findByDateInWeek(startDate.plusDays(6));

        ResScheduleDTO slot1 = new ResScheduleDTO();
        slot1.setSlot("Slot 1");
        slot1.setMonday(new ArrayList<>());
        slot1.setTuesday(new ArrayList<>());
        slot1.setWednesday(new ArrayList<>());
        slot1.setThursday(new ArrayList<>());
        slot1.setFriday(new ArrayList<>());
        slot1.setSaturday(new ArrayList<>());
        slot1.setSunday(new ArrayList<>());

        ResScheduleDTO slot2 = new ResScheduleDTO();
        slot2.setSlot("Slot 2");
        slot2.setMonday(new ArrayList<>());
        slot2.setTuesday(new ArrayList<>());
        slot2.setWednesday(new ArrayList<>());
        slot2.setThursday(new ArrayList<>());
        slot2.setFriday(new ArrayList<>());
        slot2.setSaturday(new ArrayList<>());
        slot2.setSunday(new ArrayList<>());

        ResScheduleDTO slot3 = new ResScheduleDTO();
        slot3.setSlot("Slot 3");
        slot3.setMonday(new ArrayList<>());
        slot3.setTuesday(new ArrayList<>());
        slot3.setWednesday(new ArrayList<>());
        slot3.setThursday(new ArrayList<>());
        slot3.setFriday(new ArrayList<>());
        slot3.setSaturday(new ArrayList<>());
        slot3.setSunday(new ArrayList<>());

        // Phân loại các Schedule vào từng slot
        for (Schedule s : schedules) {
            ResScheduleDTO targetSlot = null;

            if (s.getSlotNumber() == 1) {
                targetSlot = slot1;
            } else if (s.getSlotNumber() == 2) {
                targetSlot = slot2;
            } else if (s.getSlotNumber() == 3) {
                targetSlot = slot3;
            }

            if (targetSlot != null) {
                // Nhóm các ngày trong tuần của slot
                for (int weekday : s.getWeekdayList()) {
                    switch (weekday) {
                        case 2:
                            if (s.getClassInfo().getOpenDay().isBefore(startDate))
                                targetSlot.getMonday().add(s.getClassInfo());
                            break;
                        case 3:
                            if (s.getClassInfo().getOpenDay().isBefore(startDate.plusDays(1))
                                    || s.getClassInfo().getOpenDay().isEqual(startDate.plusDays(1)))
                                targetSlot.getTuesday().add(s.getClassInfo());
                            break;
                        case 4:
                            if (s.getClassInfo().getOpenDay().isBefore(startDate.plusDays(2))
                                    || s.getClassInfo().getOpenDay().isEqual(startDate.plusDays(2)))
                                targetSlot.getWednesday().add(s.getClassInfo());
                            break;
                        case 5:
                            if (s.getClassInfo().getOpenDay().isBefore(startDate.plusDays(3))
                                    || s.getClassInfo().getOpenDay().isEqual(startDate.plusDays(3)))
                                targetSlot.getThursday().add(s.getClassInfo());
                            break;
                        case 6:
                            if (s.getClassInfo().getOpenDay().isBefore(startDate.plusDays(4))
                                    || s.getClassInfo().getOpenDay().isEqual(startDate.plusDays(4)))
                                targetSlot.getFriday().add(s.getClassInfo());
                            break;
                        case 7:
                            if (s.getClassInfo().getOpenDay().isBefore(startDate.plusDays(5))
                                    || s.getClassInfo().getOpenDay().isEqual(startDate.plusDays(5)))
                                targetSlot.getSaturday().add(s.getClassInfo());
                            break;
                        case 1:
                            if (s.getClassInfo().getOpenDay().isBefore(startDate.plusDays(6))
                                    || s.getClassInfo().getOpenDay().isEqual(startDate.plusDays(6)))
                                targetSlot.getSunday().add(s.getClassInfo());
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        return Arrays.asList(slot1, slot2, slot3);
    }

    public boolean isExistBySlotNumberAndClass(int slotNumber, Class classInfo) {
        return this.scheduleRepository.existsBySlotNumberAndClassInfo(slotNumber, classInfo);
    }

}
