package com.myproject.sm.domain;

import java.util.List;

import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "campus")
public class Campus {

    @Id
    @UuidGenerator
    private String id;

    @Nationalized
    private String name;

    @Column(columnDefinition = "nvarchar(max)")
    private String address;

    @OneToMany(mappedBy = "campus")
    @JsonIgnoreProperties("campus")
    private List<Facility> facilities;

    @OneToMany(mappedBy = "campus")
    @JsonIgnore
    private List<Class> classes;

}
