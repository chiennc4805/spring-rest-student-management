package com.myproject.sm.domain;

import java.util.List;

import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @UuidGenerator
    private String id;

    @Nationalized
    private String name;

    private double pricePerDay;
    private double salaryPerDay;

    @ManyToMany(mappedBy = "subjects")
    @JsonIgnore
    private List<Teacher> teachers;

    @OneToMany(mappedBy = "subject")
    @JsonIgnore
    private List<Class> classes;

}
