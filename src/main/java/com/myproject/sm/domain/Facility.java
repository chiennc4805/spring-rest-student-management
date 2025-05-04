package com.myproject.sm.domain;

import java.time.LocalDate;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "facilities")
public class Facility {

    @Id
    @UuidGenerator
    private String id;

    private String name;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    private double cost;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "campus_id")
    @JsonIgnoreProperties("facilities")
    private Campus campus;
}
