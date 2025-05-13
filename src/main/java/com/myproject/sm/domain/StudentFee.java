package com.myproject.sm.domain;

import java.util.List;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "student_fee")
public class StudentFee {

    @Id
    @UuidGenerator
    private String id;

    private int totalAttendedDay;

    private int totalDay;

    @ElementCollection
    @CollectionTable(name = "class_fee_of_student")
    private List<ClassFee> feeOfEachClass;

    private double totalFee;

    private int month;

    private boolean status;

    @ManyToOne
    @JsonIncludeProperties({ "id", "name" })
    private Student student;

    @PrePersist
    public void handleBeforeCreate() {
        this.status = false;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public static class ClassFee {

        private String className;
        private int classAttendedDay;
        private int classTotalDay;
        private double fee;
    }
}
