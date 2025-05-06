package com.myproject.sm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.myproject.sm.domain.Campus;
import com.myproject.sm.domain.Class;
import com.myproject.sm.domain.Student;
import com.myproject.sm.domain.Subject;
import com.myproject.sm.domain.Teacher;
import com.myproject.sm.domain.dto.ClassDTO;
import com.myproject.sm.domain.dto.response.ResultPaginationDTO;
import com.myproject.sm.domain.dto.response.ResultPaginationDTO.Meta;
import com.myproject.sm.repository.ClassRepository;

@Service
public class ClassService {

    private final ClassRepository classRepository;
    private final ClassEnrollmentService classEnrollmentService;
    private final SubjectService subjectService;
    private final CampusService campusService;
    private final TeacherService teacherService;

    public ClassService(ClassRepository classRepository, ClassEnrollmentService classEnrollmentService,
            SubjectService subjectService, CampusService campusService, TeacherService teacherService) {
        this.classRepository = classRepository;
        this.classEnrollmentService = classEnrollmentService;
        this.subjectService = subjectService;
        this.campusService = campusService;
        this.teacherService = teacherService;
    }

    public ClassDTO convertClassToClassDTO(Class classInfo) {
        ClassDTO classDTO = new ClassDTO();

        classDTO.setId(classInfo.getId());
        classDTO.setName(classInfo.getName());
        classDTO.setSubject(classInfo.getSubject());
        classDTO.setCampus(classInfo.getCampus());
        classDTO.setTeacher(classInfo.getTeacher());
        classDTO.setSchedule(classInfo.getSchedule());

        List<Student> students = classInfo.getClassEnrollments().stream().map(c -> c.getEnrollmentStudent())
                .collect(Collectors.toList());

        classDTO.setStudents(students);

        return classDTO;
    }

    public Class handleCreateClass(Class reqClass) {
        if (reqClass.getTeacher() != null) {
            Teacher teacher = this.teacherService.handleFetchTeacherByTelephone(reqClass.getTeacher().getTelephone());
            reqClass.setTeacher(teacher);
        }
        if (reqClass.getCampus() != null) {
            Campus campus = this.campusService.fetchCampusById(reqClass.getCampus().getId());
            reqClass.setCampus(campus);
        }
        if (reqClass.getSubject() != null) {
            Subject subject = this.subjectService.fetchSubjectById(reqClass.getSubject().getId());
            reqClass.setSubject(subject);
        }
        return this.classRepository.save(reqClass);
    }

    public ResultPaginationDTO handleFetchAllClasses(Specification<Class> spec) {
        List<Class> classes = this.classRepository.findAll(spec) != null ? this.classRepository.findAll(spec)
                : new ArrayList<>();
        ResultPaginationDTO res = new ResultPaginationDTO();
        Meta meta = new ResultPaginationDTO.Meta();

        meta.setTotal(classes.size());

        res.setMeta(meta);
        List<ClassDTO> classDTOs = classes.stream().map(this::convertClassToClassDTO)
                .collect(Collectors.toList());

        res.setResult(classDTOs);

        return res;
    }

    public ResultPaginationDTO handleFetchAllClasses(Specification<Class> spec, Pageable pageable) {
        Page<Class> pageClass = this.classRepository.findAll(spec, pageable);
        ResultPaginationDTO res = new ResultPaginationDTO();
        Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageClass.getNumber() + 1);
        mt.setPageSize(pageClass.getSize());
        mt.setPages(pageClass.getTotalPages());
        mt.setTotal(pageClass.getTotalElements());

        res.setMeta(mt);

        List<ClassDTO> classDTOs = pageClass.getContent().stream().map(this::convertClassToClassDTO)
                .collect(Collectors.toList());

        res.setResult(classDTOs);

        return res;
    }

    public Class handleFetchClassById(String id) {
        Optional<Class> classOptional = this.classRepository.findById(id);
        Class c = classOptional.isPresent() ? classOptional.get() : null;
        return c;
    }

    public Class handleFetchClassByName(String name) {
        Optional<Class> classOptional = this.classRepository.findByName(name);
        Class c = classOptional.isPresent() ? classOptional.get() : null;
        return c;
    }

    public Class handleUpdateClass(Class reqClass) {
        if (reqClass.getTeacher() != null) {
            Teacher teacher = this.teacherService.handleFetchTeacherByTelephone(reqClass.getTeacher().getTelephone());
            reqClass.setTeacher(teacher);
        }
        if (reqClass.getCampus() != null) {
            Campus campus = this.campusService.fetchCampusById(reqClass.getCampus().getId());
            reqClass.setCampus(campus);
        }
        if (reqClass.getSubject() != null) {
            Subject subject = this.subjectService.fetchSubjectById(reqClass.getSubject().getId());
            reqClass.setSubject(subject);
        }

        return this.classRepository.save(reqClass);
    }

    public void handleDeleteClass(String id) {
        // delete in ClassEnrollment table
        Class classDB = this.handleFetchClassById(id);
        this.classEnrollmentService.deleteClassEnrollment(classDB);

        this.classRepository.deleteById(id);
    }

    public boolean isExistByName(String name) {
        return this.classRepository.existsByName(name);
    }

}
