package com.myproject.sm.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.myproject.sm.domain.Permission;
import com.myproject.sm.domain.Role;
import com.myproject.sm.domain.dto.response.ResultPaginationDTO;
import com.myproject.sm.domain.dto.response.ResultPaginationDTO.Meta;
import com.myproject.sm.repository.PermissionRepository;
import com.myproject.sm.repository.RoleRepository;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleService(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public Role handleFetchRoleById(String id) {
        Optional<Role> roleOptional = this.roleRepository.findById(id);
        return roleOptional.isPresent() ? roleOptional.get() : null;
    }

    public Role handleFetchRoleByName(String name) {
        Optional<Role> roleOptional = this.roleRepository.findByName(name);
        return roleOptional.isPresent() ? roleOptional.get() : null;
    }

    public boolean isExistByName(String name) {
        return this.roleRepository.existsByName(name);
    }

    public Role handleCreateAndUpdateRole(Role reqRole) {
        if (reqRole.getPermissions() != null) {
            List<String> permissionIds = reqRole.getPermissions().stream().map(x -> x.getId())
                    .collect(Collectors.toList());
            List<Permission> permissions = this.permissionRepository.findByIdIn(permissionIds);
            reqRole.setPermissions(permissions);
        }
        return this.roleRepository.save(reqRole);
    }

    public void handleDeleteRole(String id) {
        this.roleRepository.deleteById(id);
    }

    public ResultPaginationDTO handleFetchAllRoles(Specification<Role> spec, Pageable pageable) {
        Page<Role> pageUser = this.roleRepository.findAll(spec, pageable);
        ResultPaginationDTO res = new ResultPaginationDTO();
        Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageUser.getNumber() + 1);
        mt.setPageSize(pageUser.getSize());
        mt.setPages(pageUser.getTotalPages());
        mt.setTotal(pageUser.getTotalElements());

        res.setMeta(mt);
        res.setResult(pageUser.getContent());

        return res;
    }

}
