package com.myproject.sm.domain;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @UuidGenerator
    private String id;
    private String username;
    private String password;

    private String name;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String refreshToken;

    @ManyToOne()
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({ "user", "subjects" })
    private Teacher teacherInfo;

    @OneToOne(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({ "user", "subjects" })
    private Parent parentInfo;

}
