package com.myproject.sm.domain;

import java.util.List;

import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "roles")
@NoArgsConstructor
public class Role {

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Id
    @UuidGenerator
    private String id;

    @NotBlank(message = "Name không được để trống")
    @Nationalized
    private String name;

    @NotBlank(message = "Description không được để trống")
    @Nationalized
    private String description;

    private boolean active;

    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private List<User> users;

    @ManyToMany
    @JsonIgnoreProperties("roles")
    @JoinTable(name = "permission_role", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private List<Permission> permissions;

    @PrePersist
    public void handleBeforeCreate() {
        this.active = true;
    }

}
