package com.myproject.sm.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.myproject.sm.domain.Role;
import com.myproject.sm.domain.User;
import com.myproject.sm.domain.response.ResultPaginationDTO;
import com.myproject.sm.domain.response.ResultPaginationDTO.Meta;
import com.myproject.sm.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public User handleCreateUser(User user) {
        return this.userRepository.save(user);
    }

    public User fetchUserById(String id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        return userOptional.isPresent() ? userOptional.get() : null;
    }

    public User handleUpdateUser(User reqUser) {
        User userDB = this.fetchUserById(reqUser.getId());
        if (userDB != null) {

            // check role
            if (reqUser.getRole() != null) {
                Role role = roleService.fetchRoleById(reqUser.getRole().getId());
                userDB.setRole(role != null ? role : null);
            }

        }

        // update
        userDB = this.userRepository.save(userDB);
        return userDB;
    }

    public void handleDeleteUser(String id) {
        this.userRepository.deleteById(id);
    }

    public ResultPaginationDTO fetchAllUsers(Specification<User> spec, Pageable pageable) {
        Page<User> pageUser = this.userRepository.findAll(spec, pageable);
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

    public User handleGetUserByUsername(String username) {
        Optional<User> userOptional = this.userRepository.findByUsername(username);
        return userOptional.isPresent() ? userOptional.get() : null;
    }
}
