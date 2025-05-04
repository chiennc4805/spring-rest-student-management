package com.myproject.sm.domain;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "students")
public class Student {

    @Id
    @UuidGenerator
    private String id;

    @NotBlank(message = "Name không được để trống")
    private String name;

    private Boolean gender;

    private LocalDate birthDate;

    private double height;

    private double weight;

    @ManyToOne()
    @JoinColumn(name = "parent_id")
    @JsonIgnoreProperties("students")
    private Parent parent;

    @OneToMany(mappedBy = "enrollmentStudent")
    @JsonIgnore
    private List<ClassEnrollment> classEnrollments;

}
