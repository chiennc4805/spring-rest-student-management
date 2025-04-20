// package com.myproject.sm.domain;

// import java.util.UUID;

// import org.hibernate.annotations.ManyToAny;

// import jakarta.persistence.Entity;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;
// import jakarta.validation.constraints.NotBlank;
// import lombok.Getter;
// import lombok.Setter;

// @Getter
// @Setter
// @Entity
// @Table(name = "permissions")
// public class Permission {

// @Id
// @GeneratedValue(strategy = GenerationType.UUID)
// private UUID id;

// @NotBlank(message = "Name không được để trống")
// private String name;

// @NotBlank(message = "ApiPath không được để trống")
// private String apiPath;

// @NotBlank(message = "Method không được để trống")
// private String method;

// @NotBlank(message = "Module không được để trống")
// private String module;

// // @ManyToAny(fetch = FetchType.LAZY)
// // private Role role;

// }
