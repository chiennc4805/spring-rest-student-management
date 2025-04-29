package com.myproject.sm.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.myproject.sm.domain.Role;
import com.myproject.sm.domain.User;
import com.myproject.sm.domain.dto.response.ResultPaginationDTO;
import com.myproject.sm.domain.dto.response.ResultPaginationDTO.Meta;
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
        if (user.getRole() != null) {
            Role role = this.roleService.fetchRoleById(user.getRole().getId());
            user.setRole(role);
        }
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

    public void updateUserToken(String token, String username) {
        User currentUser = this.handleGetUserByUsername(username);
        if (currentUser != null) {
            currentUser.setRefreshToken(token);
            this.userRepository.save(currentUser);
        }
    }

    public User getUserByRefreshTokenAndUsername(String refreshToken, String username) {
        Optional<User> userOptional = this.userRepository.findByRefreshTokenAndUsername(refreshToken, username);
        return userOptional.isPresent() ? userOptional.get() : null;
    }

    public boolean isExistByUsername(String username) {
        return this.userRepository.existsByUsername(username);
    }
}
