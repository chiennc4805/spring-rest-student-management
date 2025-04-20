package com.myproject.sm.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "parents")
public class Parent {

    @Id
    @UuidGenerator
    private String id;

    private String name;
    private Boolean gender;
    private LocalDate birthDate;
    private String telephone;
    private String address;
    private String zaloName;
    private String facebookName;

    @OneToMany(mappedBy = "parent")
    private List<Student> students;

}
