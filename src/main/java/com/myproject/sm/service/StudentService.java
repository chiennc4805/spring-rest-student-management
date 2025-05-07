package com.myproject.sm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.myproject.sm.domain.Class;
import com.myproject.sm.domain.ClassEnrollment;
import com.myproject.sm.domain.Parent;
import com.myproject.sm.domain.Student;
import com.myproject.sm.domain.dto.StudentDTO;
import com.myproject.sm.domain.dto.response.ResultPaginationDTO;
import com.myproject.sm.domain.dto.response.ResultPaginationDTO.Meta;
import com.myproject.sm.repository.StudentRepository;

import jakarta.transaction.Transactional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final ParentService parentService;
    private final ClassEnrollmentService classEnrollmentService;
    private final ClassService classService;

    public StudentService(StudentRepository studentRepository, ParentService parentService,
            ClassEnrollmentService classEnrollmentService, ClassService classService) {
        this.studentRepository = studentRepository;
        this.parentService = parentService;
        this.classEnrollmentService = classEnrollmentService;
        this.classService = classService;

    }

    public Student convertStudentDTOtoStudent(StudentDTO studentDTO) {
        Student student = new Student();
        student.setId(studentDTO.getId());
        student.setBirthDate(studentDTO.getBirthDate());
        student.setGender(studentDTO.getGender());
        student.setName(studentDTO.getName());
        student.setWeight(studentDTO.getWeight());
        student.setHeight(studentDTO.getHeight());
        student.setParent(studentDTO.getParent());

        return student;
    }

    public StudentDTO convertStudentToStudentDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setBirthDate(student.getBirthDate());
        studentDTO.setGender(student.getGender());
        studentDTO.setName(student.getName());
        studentDTO.setWeight(student.getWeight());
        studentDTO.setHeight(student.getHeight());
        studentDTO.setParent(student.getParent());

        List<Class> classes = student.getClassEnrollments().stream().map(c -> c.getEnrollmentClass())
                .collect(Collectors.toList());

        studentDTO.setClasses(classes);

        return studentDTO;
    }

    public StudentDTO handleCreateStudent(StudentDTO reqStudentDTO) {
        // check parent valid
        if (reqStudentDTO.getParent() != null) {
            Parent parent = this.parentService.fetchParentByTelephone(reqStudentDTO.getParent().getTelephone());
            reqStudentDTO.setParent(parent);
        }

        // create student
        Student student = this.studentRepository.save(this.convertStudentDTOtoStudent(reqStudentDTO));
        reqStudentDTO.setId(student.getId());

        List<ClassEnrollment> classEnrollments = new ArrayList<>();
        List<Class> classes = new ArrayList<>();
        for (Class cl : reqStudentDTO.getClasses()) {
            Class classDB = this.classService.handleFetchClassById(cl.getId());
            if (classDB != null) {
                // save in ClassEnrollment table
                ClassEnrollment classEnrollment = new ClassEnrollment();
                classEnrollment.setEnrollmentStudent(student);
                classEnrollment.setEnrollmentClass(classDB);
                classEnrollment = this.classEnrollmentService.handleCreateClassEnrollment(classEnrollment);

                // save in list class of studentDTO
                classes.add(classDB);
                // save in list classEnrollment to set for student
                classEnrollments.add(classEnrollment);
            }
        }
        reqStudentDTO.setClasses(classes);

        return reqStudentDTO;
    }

    public ResultPaginationDTO handleFetchAllStudents(Specification<Student> spec, Pageable pageable) {
        Page<Student> pageStudent = this.studentRepository.findAll(spec, pageable);
        ResultPaginationDTO res = new ResultPaginationDTO();
        Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageStudent.getNumber() + 1);
        mt.setPageSize(pageStudent.getSize());
        mt.setPages(pageStudent.getTotalPages());
        mt.setTotal(pageStudent.getTotalElements());

        res.setMeta(mt);

        List<StudentDTO> studentDTOs = pageStudent.getContent().stream().map(this::convertStudentToStudentDTO)
                .collect(Collectors.toList());

        res.setResult(studentDTOs);

        return res;
    }

    public StudentDTO handleFetchStudentById(String id) {
        Optional<Student> studentOptional = this.studentRepository.findById(id);
        return studentOptional.isPresent() ? this.convertStudentToStudentDTO(studentOptional.get()) : null;
    }

    @Transactional
    public StudentDTO handleUpdateStudent(StudentDTO reqStudentDTO) {
        // check parent valid
        if (reqStudentDTO.getParent() != null) {
            Parent parent = this.parentService.fetchParentByTelephone(reqStudentDTO.getParent().getTelephone());
            reqStudentDTO.setParent(parent);
        }

        Student student = this.convertStudentDTOtoStudent(reqStudentDTO);

        StudentDTO studentRealDTO = this.convertStudentToStudentDTO(this.findStudentById(reqStudentDTO.getId()));

        // update student
        this.studentRepository.save(student);

        // update ClassEnrollment
        List<Class> classes = new ArrayList<>();
        if (reqStudentDTO.getClasses() != null && !reqStudentDTO.getClasses().isEmpty()) {
            for (Class cl : reqStudentDTO.getClasses()) {
                Class classDB = this.classService.handleFetchClassById(cl.getId());
                if (classDB != null) {
                    if (!studentRealDTO.getClasses().contains(classDB)) {
                        // save in ClassEnrollment table
                        ClassEnrollment classEnrollment = new ClassEnrollment();
                        classEnrollment.setEnrollmentStudent(student);
                        classEnrollment.setEnrollmentClass(classDB);
                        this.classEnrollmentService.handleCreateClassEnrollment(classEnrollment);
                    }

                    // add to list class of studentDTO
                    classes.add(classDB);
                }
            }
        }
        reqStudentDTO.setClasses(classes);

        // delete existing class but not exist when update
        List<Class> classesDelete = studentRealDTO.getClasses().stream()
                .filter(c -> !reqStudentDTO.getClasses().contains(c))
                .collect(Collectors.toList());
        classesDelete.forEach(c -> classEnrollmentService.deleteClassEnrollment(c));

        return reqStudentDTO;
    }

    public void handleDeleteStudent(String id) {
        // delete in ClassEnrollment table
        Student studentDB = this.findStudentById(id);
        this.classEnrollmentService.deleteClassEnrollment(studentDB);

        this.studentRepository.deleteById(id);
    }

    public Student findStudentById(String id) {
        Optional<Student> studentOptional = this.studentRepository.findById(id);
        return studentOptional.isPresent() ? studentOptional.get() : null;
    }
}
