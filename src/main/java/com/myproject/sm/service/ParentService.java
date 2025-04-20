package com.myproject.sm.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.myproject.sm.domain.Parent;
import com.myproject.sm.domain.Parent;
import com.myproject.sm.domain.response.ResultPaginationDTO;
import com.myproject.sm.domain.response.ResultPaginationDTO.Meta;
import com.myproject.sm.repository.ParentRepository;

@Service
public class ParentService {

    private final ParentRepository parentRepository;

    public ParentService(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }

    public Parent handleCreateParent(Parent parent) {
        return this.parentRepository.save(parent);
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
        if (reqParent.getName() != null) {
            parentDB.setName(reqParent.getName());
        }
        if (reqParent.getGender() != null) {
            parentDB.setGender(reqParent.getGender());
        }
        if (reqParent.getBirthDate() != null) {
            parentDB.setBirthDate(reqParent.getBirthDate());
        }
        return this.parentRepository.save(parentDB);
    }

    public void deleteParent(String id) {
        this.parentRepository.deleteById(id);
    }

}
