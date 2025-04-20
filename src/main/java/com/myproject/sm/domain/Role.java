// package com.myproject.sm.domain;

// import java.util.List;
// import java.util.UUID;

// import com.fasterxml.jackson.annotation.JsonIgnore;

// import jakarta.persistence.Entity;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.OneToMany;
// import jakarta.persistence.Table;
// import jakarta.validation.constraints.NotBlank;
// import lombok.Getter;
// import lombok.Setter;

// @Getter
// @Setter
// @Entity
// @Table(name = "roles")
// public class Role {

// @Id
// @GeneratedValue(strategy = GenerationType.UUID)
// private UUID id;

// @NotBlank(message = "Name không được để trống")
// private String name;

// @NotBlank(message = "Description không được để trống")
// private String Description;

// @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
// @JsonIgnore
// private List<User> users;
// }
