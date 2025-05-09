package com.myproject.sm.domain;

import java.time.LocalDate;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "student_attendance")
public class StudentAttendance {
    @Id
    @UuidGenerator
    private String id;

    private LocalDate date;

    private int slot;

    private boolean status;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIncludeProperties({ "id", "name" })
    private Student student;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class classInfo;

    @PrePersist
    public void handleBeforeCreate() {
        this.status = false;
    }
}
