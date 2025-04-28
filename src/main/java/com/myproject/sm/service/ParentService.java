package com.myproject.sm.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.myproject.sm.domain.Parent;
import com.myproject.sm.domain.Role;
import com.myproject.sm.domain.User;
import com.myproject.sm.domain.dto.response.ResultPaginationDTO;
import com.myproject.sm.domain.dto.response.ResultPaginationDTO.Meta;
import com.myproject.sm.repository.ParentRepository;
import com.myproject.sm.util.NumberUtil;

@Service
public class ParentService {

    private final ParentRepository parentRepository;
    private final RoleService roleService;
    private final UserService userService;

    public ParentService(ParentRepository parentRepository, RoleService roleService, UserService userService) {
        this.parentRepository = parentRepository;
        this.roleService = roleService;
        this.userService = userService;
    }

    public Parent handleCreateParent(Parent reqParent) {
        // create user has role PARENT
        Role role = this.roleService.handleFetchRoleByName("PARENT");
        User newUser = new User(reqParent.getTelephone(), NumberUtil.getRandomNumberString(), role);
        User user = userService.handleCreateUser(newUser);
        reqParent.setUser(user);

        return this.parentRepository.save(reqParent);
    }

    public ResultPaginationDTO fetchAllParents(Specification<Parent> spec, Pageable pageable) {
        Page<Parent> pageParent = this.parentRepository.findAll(spec, pageable);
        ResultPaginationDTO res = new ResultPaginationDTO();
        Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageParent.getNumber() + 1);
        mt.setPageSize(pageParent.getSize());
        mt.setPages(pageParent.getTotalPages());
        mt.setTotal(pageParent.getTotalElements());

        res.setMeta(mt);
        res.setResult(pageParent.getContent());

        return res;
    }

    public Parent fetchParentById(String id) {
        Optional<Parent> parentOptional = this.parentRepository.findById(id);
        Parent p = parentOptional.isPresent() ? parentOptional.get() : null;
        return p;
    }

    public Parent updateParent(Parent reqParent) {
        Parent parentDB = this.fetchParentById(reqParent.getId());
        // update Parent
        parentDB.setName(reqParent.getName());
        parentDB.setGender(reqParent.getGender());
        parentDB.setBirthDate(reqParent.getBirthDate());
        parentDB.setAddress(reqParent.getAddress());
        parentDB.setTelephone(reqParent.getTelephone());
        parentDB.setFacebookName(reqParent.getFacebookName());
        parentDB.setZaloName(reqParent.getZaloName());

        return this.parentRepository.save(parentDB);
    }

    public void deleteParent(String id) {
        this.parentRepository.deleteById(id);
    }

    public boolean isExistByTelephone(String telephone) {
        return this.parentRepository.existsByTelephone(telephone);
    }

}
