package com.myproject.sm.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.myproject.sm.domain.Subject;
import com.myproject.sm.domain.response.ResultPaginationDTO;
import com.myproject.sm.domain.response.ResultPaginationDTO.Meta;
import com.myproject.sm.repository.SubjectRepository;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public Subject handleCreateSubject(Subject reqSubject) {
        return this.subjectRepository.save(reqSubject);
    }

    public ResultPaginationDTO fetchAllSubjects(Specification<Subject> spec, Pageable pageable) {
        Page<Subject> pageSubject = this.subjectRepository.findAll(spec, pageable);
        ResultPaginationDTO res = new ResultPaginationDTO();
        Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageSubject.getNumber() + 1);
        mt.setPageSize(pageSubject.getSize());
        mt.setPages(pageSubject.getTotalPages());
        mt.setTotal(pageSubject.getTotalElements());

        res.setMeta(mt);
        res.setResult(pageSubject.getContent());

        return res;
    }

    public Subject fetchSubjectById(String id) {
        Optional<Subject> subjectOptional = this.subjectRepository.findById(id);
        Subject sub = subjectOptional.isPresent() ? subjectOptional.get() : null;
        return sub;
    }

    public Subject updateSubject(Subject reqSubject) {
        Subject subjectDB = this.fetchSubjectById(reqSubject.getId());
        // update subject
        subjectDB.setName(reqSubject.getName());
        subjectDB.setPricePerDay(reqSubject.getPricePerDay());
        subjectDB.setSalaryPerDay(reqSubject.getSalaryPerDay());

        return this.subjectRepository.save(subjectDB);
    }

    public void deleteSubject(String id) {
        // delete subject inside teacher_skill
        Subject subject = this.fetchSubjectById(id);
        subject.getTeachers().forEach(t -> t.getSubjects().remove(subject));

        // delete subject in subject table
        this.subjectRepository.deleteById(id);
    }

    public Subject fetchSubjectByName(String name) {
        Optional<Subject> subjectOptional = this.subjectRepository.findByName(name);
        return subjectOptional.isPresent() ? subjectOptional.get() : null;
    }

    public boolean isExistByName(String name) {
        return this.subjectRepository.existsByName(name);
    }

}
