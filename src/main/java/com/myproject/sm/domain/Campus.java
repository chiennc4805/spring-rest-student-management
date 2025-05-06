package com.myproject.sm.domain;

import java.util.List;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "campus")
@NoArgsConstructor
public class Campus {

    public Campus(String name, String address) {
        this.name = name;
        this.address = address;
    }

    @Id
    @UuidGenerator
    private String id;

    private String name;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String address;

    @OneToMany(mappedBy = "campus")
    @JsonIgnoreProperties("campus")
    private List<Facility> facilities;

    @OneToMany(mappedBy = "campus")
    @JsonIgnore
    private List<Class> classes;

}
