package com.myproject.sm.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.myproject.sm.domain.Role;
import com.myproject.sm.repository.RoleRepository;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role fetchRoleById(String id) {
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

}
