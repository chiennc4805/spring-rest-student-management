package com.myproject.sm.domain;

import java.time.LocalDate;

import org.hibernate.annotations.UuidGenerator;

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
@Table(name = "class_enrollments")
public class ClassEnrollment {

    @Id
    @UuidGenerator
    private String id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student enrollmentStudent;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class enrollmentClass;

    private LocalDate date;

    @PrePersist
    public void handleBeforeCreate() {
        this.date = LocalDate.now();
    }

}
