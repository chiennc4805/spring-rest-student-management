package com.myproject.sm.domain.dto;

import java.util.List;

import com.myproject.sm.domain.Campus;
import com.myproject.sm.domain.Schedule;
import com.myproject.sm.domain.Student;
import com.myproject.sm.domain.Subject;
import com.myproject.sm.domain.Teacher;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassDTO {
    private String id;

    private String name;

    private Subject subject;

    private Campus campus;

    private Teacher teacher;

    private Schedule schedule;

    private List<Student> students;
}
