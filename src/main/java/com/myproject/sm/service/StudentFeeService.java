package com.myproject.sm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.myproject.sm.domain.Class;
import com.myproject.sm.domain.Student;
import com.myproject.sm.domain.StudentAttendance;
import com.myproject.sm.domain.StudentFee;
import com.myproject.sm.domain.StudentFee.ClassFee;
import com.myproject.sm.domain.dto.request.ReqCreateStudentFee;
import com.myproject.sm.domain.dto.response.ResultPaginationDTO;
import com.myproject.sm.repository.StudentAttendanceRepository;
import com.myproject.sm.repository.StudentFeeRepository;
import com.myproject.sm.repository.StudentRepository;

@Service
public class StudentFeeService {

    private final StudentFeeRepository studentFeeRepository;
    private final StudentRepository studentRepository;
    private final StudentAttendanceService studentAttendanceService;
    private final StudentAttendanceRepository studentAttendanceRepository;

    public StudentFeeService(StudentFeeRepository studentFeeRepository, StudentRepository studentRepository,
            StudentAttendanceService studentAttendanceService,
            StudentAttendanceRepository studentAttendanceRepository) {
        this.studentFeeRepository = studentFeeRepository;
        this.studentRepository = studentRepository;
        this.studentAttendanceService = studentAttendanceService;
        this.studentAttendanceRepository = studentAttendanceRepository;
    }

    public List<StudentFee> handleCreateStudentFee(ReqCreateStudentFee reqStudentFee) {
        Specification<Student> spec = (root, query, builder) -> builder.isNotEmpty(root.get("classEnrollments"));

        List<Student> students = this.studentRepository.findAll(spec);
        List<StudentFee> studentFees = new ArrayList<>();
        for (Student s : students) {
            List<Class> listClassOfStudent = s.getClassEnrollments().stream().map(ce -> ce.getEnrollmentClass())
                    .collect(Collectors.toList());
            List<StudentAttendance> studentAttendances = this.studentAttendanceRepository.findByStudentAndMonthOfDate(s,
                    reqStudentFee.getMonth());

            int totalDay = 0;
            int totalAttendedDay = 0;
            double totalFee = 0;
            StudentFee sf = new StudentFee();
            List<ClassFee> listClassFee = new ArrayList<>();

            for (Class classEnrollment : listClassOfStudent) {
                List<StudentAttendance> studentAttendancesInCurrentClass = studentAttendances.stream()
                        .filter(sa -> sa.getClassInfo().getName().equals(classEnrollment.getName()))
                        .collect(Collectors.toList());

                ClassFee classFee = new StudentFee.ClassFee();
                int classTotalDay = studentAttendancesInCurrentClass.size();
                int classAttendedDay = studentAttendancesInCurrentClass.stream().filter(sa -> sa.isStatus())
                        .collect(Collectors.toList()).size();
                double fee = classAttendedDay * classEnrollment.getSubject().getPricePerDay();
                classFee.setClassName(classEnrollment.getName());
                classFee.setClassAttendedDay(classAttendedDay);
                classFee.setClassTotalDay(classTotalDay);
                classFee.setFee(fee);
                listClassFee.add(classFee);

                totalDay += classTotalDay;
                totalAttendedDay += classAttendedDay;
                totalFee += fee;
            }
            sf.setFeeOfEachClass(listClassFee);
            sf.setTotalDay(totalDay);
            sf.setTotalAttendedDay(totalAttendedDay);
            sf.setTotalFee(totalFee);
            sf.setMonth(reqStudentFee.getMonth());
            sf.setStudent(s);
            studentFees.add(sf);
        }

        return this.studentFeeRepository.saveAll(studentFees);
    }

    public ResultPaginationDTO handleFetchAllStudentFees(Specification<StudentFee> spec, Pageable pageable) {
        Page<StudentFee> pageStudentFee = this.studentFeeRepository.findAll(spec, pageable);

        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(pageStudentFee.getTotalPages());
        mt.setTotal(pageStudentFee.getTotalElements());

        rs.setMeta(mt);

        rs.setResult(pageStudentFee.getContent());
        return rs;
    }

    public List<StudentFee> handleFetchAllStudentFees(Specification<StudentFee> spec) {
        return this.studentFeeRepository.findAll(spec);
    }

}
