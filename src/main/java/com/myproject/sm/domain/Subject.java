package com.myproject.sm.domain;

import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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

}
