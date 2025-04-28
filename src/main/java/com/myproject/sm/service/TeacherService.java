package com.myproject.sm.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.myproject.sm.domain.Role;
import com.myproject.sm.domain.Subject;
import com.myproject.sm.domain.Teacher;
import com.myproject.sm.domain.User;
import com.myproject.sm.domain.dto.response.ResultPaginationDTO;
import com.myproject.sm.repository.SubjectRepository;
import com.myproject.sm.repository.TeacherRepository;
import com.myproject.sm.util.NumberUtil;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final RoleService roleService;
    private final UserService userService;

    public TeacherService(TeacherRepository teacherRepository, SubjectRepository subjectRepository,
            RoleService roleService, UserService userService) {
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
        this.roleService = roleService;
        this.userService = userService;
    }

    public Teacher handleCreateTeacher(Teacher reqTeacher) {
        // create user has role TEACHER
        Role role = this.roleService.handleFetchRoleByName("TEACHER");
        User newUser = new User(reqTeacher.getTelephone(), NumberUtil.getRandomNumberString(), role);
        User user = userService.handleCreateUser(newUser);
        reqTeacher.setUser(user);

        // create teacher
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

    public boolean isExistByTelephone(String telephone) {
        return this.teacherRepository.existsByTelephone(telephone);
    }

    public Teacher handleFetchTeacherByTelephone(String telephone) {
        Optional<Teacher> teacherOptional = this.teacherRepository.findByTelephone(telephone);
        return teacherOptional.isPresent() ? teacherOptional.get() : null;
    }

}
