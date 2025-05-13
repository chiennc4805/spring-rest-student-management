package com.myproject.sm.domain;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PreRemove;
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

    @OneToMany(mappedBy = "parent", orphanRemoval = true, cascade = { CascadeType.PERSIST })
    @JsonIgnoreProperties("parent")
    private List<Student> students;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @PreRemove
    public void handleBeforeRemove() {
        students.forEach(s -> s.setParent(null));
        this.setStudents(null);
    }

}
