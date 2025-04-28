package com.myproject.sm.domain.dto;

import java.time.LocalDate;
import java.util.List;

import com.myproject.sm.domain.Class;
import com.myproject.sm.domain.Parent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDTO {

    private String id;

    private String name;

    private Boolean gender;

    private LocalDate birthDate;

    private Parent parent;

    private List<Class> classes;

}
