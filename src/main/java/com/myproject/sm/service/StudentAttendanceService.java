package com.myproject.sm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.myproject.sm.domain.Class;
import com.myproject.sm.domain.Student;
import com.myproject.sm.domain.StudentAttendance;
import com.myproject.sm.domain.dto.request.ReqCreateAttendance;
import com.myproject.sm.domain.dto.request.ReqUpdateStudentAttendance;
import com.myproject.sm.domain.dto.response.ResultPaginationDTO;
import com.myproject.sm.domain.dto.response.ResultPaginationDTO.Meta;
import com.myproject.sm.repository.StudentAttendanceRepository;

import jakarta.transaction.Transactional;

@Service
public class StudentAttendanceService {

    private final StudentAttendanceRepository studentAttendanceRepository;
    private final ClassService classService;

    public StudentAttendanceService(StudentAttendanceRepository studentAttendanceRepository,
            ClassService classService) {
        this.studentAttendanceRepository = studentAttendanceRepository;
        this.classService = classService;
    }

    @Transactional
    public List<StudentAttendance> handleCreateStudentAttendance(ReqCreateAttendance resStudentAttendance) {
        if (resStudentAttendance.getClassInfo() != null) {
            Class classDB = this.classService.handleFetchClassById(resStudentAttendance.getClassInfo().getId());
            if (classDB != null) {
                List<Student> students = classDB.getClassEnrollments().stream().map(c -> c.getEnrollmentStudent())
                        .collect(Collectors.toList());
                List<StudentAttendance> studentAttendances = new ArrayList<>();
                for (Student s : students) {
                    StudentAttendance sa = new StudentAttendance();
                    sa.setClassInfo(classDB);
                    sa.setDate(resStudentAttendance.getDate());
                    sa.setSlot(resStudentAttendance.getSlot());
                    sa.setStudent(s);
                    studentAttendances.add(sa);
                }
                return this.studentAttendanceRepository.saveAll(studentAttendances);
            }
        }
        return null;
    }

    public ResultPaginationDTO handleFetchAllStudentAttendances(Specification<StudentAttendance> spec) {
        List<StudentAttendance> studentAttendances = this.studentAttendanceRepository.findAll(spec);
        ResultPaginationDTO res = new ResultPaginationDTO();
        Meta mt = new ResultPaginationDTO.Meta();

        mt.setTotal(studentAttendances.size());

        res.setMeta(mt);
        res.setResult(studentAttendances);

        return res;
    }

    public List<StudentAttendance> handleUpdateStudentAttendance(
            List<ReqUpdateStudentAttendance> reqUpdateStudentAttendances) {
        List<StudentAttendance> listUpdate = new ArrayList<>();
        for (ReqUpdateStudentAttendance reqUpdate : reqUpdateStudentAttendances) {
            Optional<StudentAttendance> saOptional = this.studentAttendanceRepository
                    .findById(reqUpdate.getAttendanceId());
            if (saOptional.isPresent()) {
                StudentAttendance sa = saOptional.get();
                sa.setStatus(reqUpdate.isStatus());
                sa.setStatusOfClass(true);
                listUpdate.add(sa);
            }
        }
        if (!listUpdate.isEmpty()) {
            return this.studentAttendanceRepository.saveAll(listUpdate);
        }
        return null;
    }

}
