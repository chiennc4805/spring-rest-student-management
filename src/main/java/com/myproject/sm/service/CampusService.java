package com.myproject.sm.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.myproject.sm.domain.Campus;
import com.myproject.sm.domain.response.ResultPaginationDTO;
import com.myproject.sm.domain.response.ResultPaginationDTO.Meta;
import com.myproject.sm.repository.CampusRepository;

@Service
public class CampusService {

    private final CampusRepository campusRepository;

    public CampusService(CampusRepository campusRepository) {
        this.campusRepository = campusRepository;
    }

    public Campus handleCreateCampus(Campus reqCampus) {
        return this.campusRepository.save(reqCampus);
    }

    public ResultPaginationDTO fetchAllCampus(Specification<Campus> spec, Pageable pageable) {
        Page<Campus> pageCampus = this.campusRepository.findAll(spec, pageable);
        ResultPaginationDTO res = new ResultPaginationDTO();
        Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageCampus.getNumber() + 1);
        mt.setPageSize(pageCampus.getSize());
        mt.setPages(pageCampus.getTotalPages());
        mt.setTotal(pageCampus.getTotalElements());

        res.setMeta(mt);
        res.setResult(pageCampus.getContent());

        return res;
    }

    public Campus fetchCampusById(String id) {
        Optional<Campus> campusOptional = this.campusRepository.findById(id);
        Campus campus = campusOptional.isPresent() ? campusOptional.get() : null;
        return campus;
    }

    public Campus updateCampus(Campus reqCampus) {
        Campus campusDB = this.fetchCampusById(reqCampus.getId());
        // update
        campusDB.setName(reqCampus.getName());
        campusDB.setAddress(reqCampus.getAddress());

        return this.campusRepository.save(campusDB);
    }

    public void deleteCampus(String id) {
        this.campusRepository.deleteById(id);
    }

    public boolean isExistByName(String name) {
        return this.campusRepository.existsByName(name);
    }

}
