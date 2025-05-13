package com.myproject.sm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.myproject.sm.domain.Class;
import com.myproject.sm.domain.Teacher;
import com.myproject.sm.domain.TeacherAttendance;
import com.myproject.sm.domain.dto.request.ReqCreateAttendance;
import com.myproject.sm.domain.dto.request.ReqUpdateStudentAttendance;
import com.myproject.sm.domain.dto.response.ResultPaginationDTO;
import com.myproject.sm.domain.dto.response.ResultPaginationDTO.Meta;
import com.myproject.sm.repository.TeacherAttendanceRepository;

import jakarta.transaction.Transactional;

@Service
public class TeacherAttendanceService {

    private final TeacherAttendanceRepository teacherAttendanceRepository;
    private final ClassService classService;

    public TeacherAttendanceService(TeacherAttendanceRepository teacherAttendanceRepository,
            ClassService classService) {
        this.teacherAttendanceRepository = teacherAttendanceRepository;
        this.classService = classService;
    }

    @Transactional
    public TeacherAttendance handleCreateTeacherAttendance(ReqCreateAttendance resTeacherAttendance) {
        if (resTeacherAttendance.getClassInfo() != null) {
            Class classDB = this.classService.handleFetchClassById(resTeacherAttendance.getClassInfo().getId());
            if (classDB != null) {
                Teacher teacher = classDB.getTeacher();
                if (teacher != null) {
                    TeacherAttendance ta = new TeacherAttendance();
                    ta.setClassInfo(classDB);
                    ta.setDate(resTeacherAttendance.getDate());
                    ta.setSlot(resTeacherAttendance.getSlot());
                    ta.setTeacher(teacher);
                    return this.teacherAttendanceRepository.save(ta);
                }
            }
        }
        return null;
    }

    public ResultPaginationDTO handleFetchAllTeacherAttendances(Specification<TeacherAttendance> spec) {
        List<TeacherAttendance> teacherAttendances = this.teacherAttendanceRepository.findAll(spec);
        ResultPaginationDTO res = new ResultPaginationDTO();
        Meta mt = new ResultPaginationDTO.Meta();

        mt.setTotal(teacherAttendances.size());

        res.setMeta(mt);
        res.setResult(teacherAttendances);

        return res;
    }

    public TeacherAttendance handleUpdateTeacherAttendance(ReqUpdateStudentAttendance reqUpdateTeacherAttendance) {
        Optional<TeacherAttendance> taOptional = this.teacherAttendanceRepository
                .findById(reqUpdateTeacherAttendance.getAttendanceId());
        if (taOptional.isPresent()) {
            TeacherAttendance ta = taOptional.get();
            ta.setStatus(reqUpdateTeacherAttendance.isStatus());
            ta.setStatusOfClass(true);
            return this.teacherAttendanceRepository.save(ta);
        }
        return null;
    }

}
