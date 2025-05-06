package com.myproject.sm.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.sm.domain.Class;
import com.myproject.sm.domain.dto.ClassDTO;
import com.myproject.sm.domain.dto.response.ResultPaginationDTO;
import com.myproject.sm.service.ClassService;
import com.myproject.sm.service.TeacherService;
import com.myproject.sm.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;

@RestController
public class ClassController {

    private final ClassService classService;
    private final TeacherService teacherService;

    public ClassController(ClassService classService, TeacherService teacherService) {
        this.classService = classService;
        this.teacherService = teacherService;
    }

    @PostMapping("/classes")
    public ResponseEntity<Class> createClass(@RequestBody Class reqClass) throws IdInvalidException {
        if (this.classService.isExistByName(reqClass.getName())) {
            throw new IdInvalidException("Lớp học với tên " + reqClass.getName() + " đã tồn tại");
        }
        boolean isExistTeacher = this.teacherService.isExistByTelephone(reqClass.getTeacher().getTelephone());
        if (!isExistTeacher) {
            throw new IdInvalidException(
                    "Giáo viên với số điện thoại " + reqClass.getTeacher().getTelephone() + " không tồn tại");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(this.classService.handleCreateClass(reqClass));
    }

    @GetMapping("/classes")
    public ResponseEntity<ResultPaginationDTO> fetchAllClasses(
            @Filter Specification<Class> spec,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        if (page == null && size == null) {
            return ResponseEntity.ok(this.classService.handleFetchAllClasses(spec));
        } else {
            Pageable pageable = PageRequest.of(page - 1, size);
            return ResponseEntity.ok(this.classService.handleFetchAllClasses(spec, pageable));
        }
    }

    @GetMapping("/classes/{name}")
    public ResponseEntity<ClassDTO> fetchClassByName(@PathVariable("name") String name) throws IdInvalidException {
        Class classDB = this.classService.handleFetchClassByName(name);
        if (classDB == null) {
            throw new IdInvalidException("Class với tên = " + name + " không tồn tại");
        }
        return ResponseEntity.ok(this.classService.convertClassToClassDTO(classDB));
    }

    @PutMapping("/classes")
    public ResponseEntity<Class> updateClass(@RequestBody Class reqClass) throws IdInvalidException {
        Class classDB = this.classService.handleFetchClassById(reqClass.getId());
        if (classDB == null) {
            throw new IdInvalidException("Class với id = " + reqClass.getId() + " không tồn tại");
        }
        if (!reqClass.getName().equals(classDB.getName()) && this.classService.isExistByName(reqClass.getName())) {
            throw new IdInvalidException("Lớp học với tên " + reqClass.getName() + " đã tồn tại");
        }
        boolean isExistTeacher = this.teacherService.isExistByTelephone(reqClass.getTeacher().getTelephone());
        if (!isExistTeacher) {
            throw new IdInvalidException(
                    "Giáo viên với số điện thoại " + reqClass.getTeacher().getTelephone() + " không tồn tại");
        }

        Class updatedStudent = this.classService.handleUpdateClass(reqClass);

        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/classes/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable("id") String id) throws IdInvalidException {
        Class classDB = this.classService.handleFetchClassById(id);
        if (classDB == null) {
            throw new IdInvalidException("Class với id = " + id + " không tồn tại");
        }
        this.classService.handleDeleteClass(id);
        return ResponseEntity.ok(null);
    }

}
