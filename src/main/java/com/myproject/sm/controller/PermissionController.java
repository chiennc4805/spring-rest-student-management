package com.myproject.sm.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.sm.domain.Permission;
import com.myproject.sm.domain.dto.response.ResultPaginationDTO;
import com.myproject.sm.service.PermissionService;
import com.turkraft.springfilter.boot.Filter;

@RestController
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/permissions")
    public ResponseEntity<ResultPaginationDTO> fetchAllPermissions(
            @Filter Specification<Permission> spec,
            Pageable pageable) {

        return ResponseEntity.ok(this.permissionService.handleFetchAllPermissions(spec, pageable));
    }

}
