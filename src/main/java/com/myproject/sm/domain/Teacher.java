package com.myproject.sm.domain;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @UuidGenerator
    private String id;

    @Nationalized
    private String name;

    private boolean gender;

    private LocalDate birthDate;

    private String telephone;

    @Nationalized
    private String address;

    @Nationalized
    private String zaloName;

    @Nationalized
    private String facebookName;

    @ManyToMany
    @JoinTable(name = "teacher_skills", joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
    @JsonIgnoreProperties("teachers")
    private List<Subject> subjects;

    @OneToMany(mappedBy = "teacher")
    @JsonIgnore
    private List<Class> classes;

}
