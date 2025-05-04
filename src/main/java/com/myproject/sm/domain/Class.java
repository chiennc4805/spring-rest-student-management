package com.myproject.sm.domain;

import java.util.List;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "classes")
public class Class {

    @Id
    @UuidGenerator
    private String id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    @JsonIncludeProperties({ "id", "name" })
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "campus_id")
    @JsonIncludeProperties({ "id", "name", "address" })
    private Campus campus;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    @JsonIncludeProperties({ "id", "name", "telephone" })
    private Teacher teacher;

    @OneToMany(mappedBy = "classInfo")
    @JsonIncludeProperties("id")
    private List<Schedule> schedules;

}
