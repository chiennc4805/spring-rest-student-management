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
@Table(name = "teacher_attendance")
public class TeacherAttendance {

    @Id
    @UuidGenerator
    private String id;

    private LocalDate date;

    private int slot;

    private boolean status;

    private boolean statusOfClass;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    @JsonIncludeProperties({ "id", "name" })
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class classInfo;

    @PrePersist
    public void handleBeforeCreate() {
        this.status = false;
        this.statusOfClass = false;
    }
}
