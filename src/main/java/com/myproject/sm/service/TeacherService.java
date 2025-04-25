package com.myproject.sm.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.myproject.sm.domain.Subject;
import com.myproject.sm.domain.Teacher;
import com.myproject.sm.domain.response.ResultPaginationDTO;
import com.myproject.sm.repository.SubjectRepository;
import com.myproject.sm.repository.TeacherRepository;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;

    public TeacherService(TeacherRepository teacherRepository, SubjectRepository subjectRepository) {
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
    }

    public Teacher handleCreateTeacher(Teacher reqTeacher) {
        if (reqTeacher.getSubjects() != null) {
            List<String> listSubjectIds = reqTeacher.getSubjects().stream().map(x -> x.getId())
                    .collect(Collectors.toList());
            List<Subject> subjects = this.subjectRepository.findByIdIn(listSubjectIds);
            reqTeacher.setSubjects(subjects);
        }

        return this.teacherRepository.save(reqTeacher);
    }

    public Teacher handleFetchTeacherById(String id) {
        Optional<Teacher> teacherOptional = this.teacherRepository.findById(id);
        return teacherOptional.isPresent() ? teacherOptional.get() : null;
    }

    public Teacher handleUpdateTeacher(Teacher reqTeacher) {
        return this.teacherRepository.save(reqTeacher);
    }

    public ResultPaginationDTO handleFetchAllTeachers(Specification<Teacher> spec, Pageable pageable) {
        Page<Teacher> pageTeacher = this.teacherRepository.findAll(spec, pageable);

        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(pageTeacher.getTotalPages());
        mt.setTotal(pageTeacher.getTotalElements());

        rs.setMeta(mt);

        rs.setResult(pageTeacher.getContent());
        return rs;
    }

    public void deleteTeacher(String id) {
        this.teacherRepository.deleteById(id);
    }

}
