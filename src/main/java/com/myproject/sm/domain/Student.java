package com.myproject.sm.domain;

import java.time.Instant;
import java.time.LocalDate;

import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private String name;

    // @NotBlank(message = "Gender không được để trống")
    private Boolean gender;

    // @NotNull(message = "BirthDate không được để trống")
    private LocalDate birthDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    private Parent parent;

}
