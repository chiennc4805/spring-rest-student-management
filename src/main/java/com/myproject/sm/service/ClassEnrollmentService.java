package com.myproject.sm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.myproject.sm.domain.Class;
import com.myproject.sm.domain.ClassEnrollment;
import com.myproject.sm.domain.Student;
import com.myproject.sm.domain.dto.response.ResultPaginationDTO;
import com.myproject.sm.domain.dto.response.ResultPaginationDTO.Meta;
import com.myproject.sm.repository.ClassEnrollmentRepository;

@Service
public class ClassEnrollmentService {

    private final ClassEnrollmentRepository classEnrollmentRepository;

    public ClassEnrollmentService(ClassEnrollmentRepository classEnrollmentRepository) {
        this.classEnrollmentRepository = classEnrollmentRepository;
    }

    public ClassEnrollment handleCreateClassEnrollment(ClassEnrollment reqClassEnrollment) {
        return this.classEnrollmentRepository.save(reqClassEnrollment);
    }

    public ClassEnrollment handleUpdateClassEnrollment(ClassEnrollment reqClassEnrollment) {
        return this.classEnrollmentRepository.save(reqClassEnrollment);
    }

    public void deleteClassEnrollment(Object object) {
        List<ClassEnrollment> classEnrollments = null;
        if (object instanceof Class cl) {
            classEnrollments = this.classEnrollmentRepository.findByEnrollmentClass(cl);
        } else if (object instanceof Student student) {
            classEnrollments = this.classEnrollmentRepository.findByEnrollmentStudent(student);
        }
        if (classEnrollments != null) {
            classEnrollments.stream().forEach(c -> this.classEnrollmentRepository.delete(c));
        }
    }

    public ResultPaginationDTO handleFetchAllStudents(Specification<ClassEnrollment> spec, Pageable pageable) {
        Page<ClassEnrollment> pageClassEnrollment = this.classEnrollmentRepository.findAll(spec, pageable);
        ResultPaginationDTO res = new ResultPaginationDTO();
        Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageClassEnrollment.getNumber() + 1);
        mt.setPageSize(pageClassEnrollment.getSize());
        mt.setPages(pageClassEnrollment.getTotalPages());
        mt.setTotal(pageClassEnrollment.getTotalElements());

        res.setMeta(mt);

        return res;
    }

    public ResultPaginationDTO handleFetchAllClassEnrollments(Specification<ClassEnrollment> spec) {
        List<ClassEnrollment> classEnrollments = this.classEnrollmentRepository.findAll(spec);
        ResultPaginationDTO res = new ResultPaginationDTO();
        Meta mt = new ResultPaginationDTO.Meta();

        mt.setTotal(classEnrollments.size());

        res.setMeta(mt);
        res.setResult(classEnrollments);

        return res;
    }

    public ResultPaginationDTO handleFetchAllClassEnrollments(Specification<ClassEnrollment> spec, Pageable pageable) {
        Page<ClassEnrollment> pageClassEnrollment = this.classEnrollmentRepository.findAll(spec, pageable);
        ResultPaginationDTO res = new ResultPaginationDTO();
        Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageClassEnrollment.getNumber() + 1);
        mt.setPageSize(pageClassEnrollment.getSize());
        mt.setPages(pageClassEnrollment.getTotalPages());
        mt.setTotal(pageClassEnrollment.getTotalElements());

        res.setMeta(mt);
        res.setResult(pageClassEnrollment.getContent());

        return res;
    }

}
