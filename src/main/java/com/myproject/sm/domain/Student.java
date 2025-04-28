package com.myproject.sm.domain;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @Nationalized
    private String name;

    // @NotBlank(message = "Gender không được để trống")
    private Boolean gender;

    // @NotNull(message = "BirthDate không được để trống")
    private LocalDate birthDate;

    @ManyToOne()
    @JoinColumn(name = "parent_id")
    @JsonIgnoreProperties("students")
    private Parent parent;

    @OneToMany(mappedBy = "enrollmentStudent")
    @JsonIgnore
    private List<ClassEnrollment> classEnrollments;

}
