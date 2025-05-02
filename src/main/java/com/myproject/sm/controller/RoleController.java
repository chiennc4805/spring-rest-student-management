package com.myproject.sm.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.sm.domain.Role;
import com.myproject.sm.domain.dto.response.ResultPaginationDTO;
import com.myproject.sm.service.RoleService;
import com.myproject.sm.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;

@RestController
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/roles")
    public ResponseEntity<Role> createRole(@Valid @RequestBody Role reqRole) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.roleService.handleCreateAndUpdateRole(reqRole));
    }

    @GetMapping("/roles")
    public ResponseEntity<ResultPaginationDTO> fetchAllRoles(
            @Filter Specification<Role> spec,
            Pageable pageable) {

        return ResponseEntity.ok(this.roleService.handleFetchAllRoles(spec, pageable));
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<Role> fetchRoleById(@PathVariable("id") String id) throws IdInvalidException {
        Role role = this.roleService.handleFetchRoleById(id);
        if (role == null) {
            throw new IdInvalidException("Role with id = " + id + " không tồn tại");
        }

        return ResponseEntity.ok(role);
    }

    @PutMapping("/roles")
    public ResponseEntity<Role> updateStudent(@RequestBody Role reqRole) throws IdInvalidException {
        Role role = this.roleService.handleFetchRoleById(reqRole.getId());
        if (role == null) {
            throw new IdInvalidException("Role with id = " + reqRole.getId() + " không tồn tại");
        }

        return ResponseEntity.ok(this.roleService.handleCreateAndUpdateRole(reqRole));
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") String id) throws IdInvalidException {
        Role role = this.roleService.handleFetchRoleById(id);
        if (role == null) {
            throw new IdInvalidException("Role with id = " + id + " không tồn tại");
        }
        this.roleService.handleDeleteRole(id);
        return ResponseEntity.ok(null);
    }

}
