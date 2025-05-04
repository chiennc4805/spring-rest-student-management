package com.myproject.sm.domain;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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

    private String address;

    private String zaloName;

    private String facebookName;

    @ManyToMany
    @JoinTable(name = "teacher_skills", joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
    @JsonIncludeProperties({ "id", "name" })
    private List<Subject> subjects;

    @OneToMany(mappedBy = "teacher")
    @JsonIgnore
    private List<Class> classes;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

}
