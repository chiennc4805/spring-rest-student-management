package com.myproject.sm.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.myproject.sm.domain.Campus;
import com.myproject.sm.domain.Facility;
import com.myproject.sm.domain.response.ResultPaginationDTO;
import com.myproject.sm.domain.response.ResultPaginationDTO.Meta;
import com.myproject.sm.repository.FacilityRepository;

@Service
public class FacilityService {

    private final FacilityRepository facilityRepository;
    private final CampusService campusService;

    public FacilityService(FacilityRepository facilityRepository, CampusService campusService) {
        this.facilityRepository = facilityRepository;
        this.campusService = campusService;
    }

    public Facility handleCreateFacility(Facility reqFacility) {
        if (reqFacility.getCampus() != null) {
            Campus campus = this.campusService.fetchCampusById(reqFacility.getCampus().getId());
            reqFacility.setCampus(campus);
        }
        return this.facilityRepository.save(reqFacility);
    }

    public ResultPaginationDTO fetchAllFacilities(Specification<Facility> spec, Pageable pageable) {
        Page<Facility> pageFacility = this.facilityRepository.findAll(spec, pageable);
        ResultPaginationDTO res = new ResultPaginationDTO();
        Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageFacility.getNumber() + 1);
        mt.setPageSize(pageFacility.getSize());
        mt.setPages(pageFacility.getTotalPages());
        mt.setTotal(pageFacility.getTotalElements());

        res.setMeta(mt);
        res.setResult(pageFacility.getContent());

        return res;
    }

    public Facility fetchFacilityById(String id) {
        Optional<Facility> facilityOptional = this.facilityRepository.findById(id);
        Facility facility = facilityOptional.isPresent() ? facilityOptional.get() : null;
        return facility;
    }

    public Facility updateFacility(Facility reqFacility) {
        Facility facilityDB = this.fetchFacilityById(reqFacility.getId());

        // update
        facilityDB.setName(reqFacility.getName());
        facilityDB.setDescription(reqFacility.getDescription());
        facilityDB.setCost(reqFacility.getCost());
        facilityDB.setDate(reqFacility.getDate());
        // check campus exist
        if (reqFacility.getCampus() != null) {
            Campus campus = this.campusService.fetchCampusById(reqFacility.getCampus().getId());
            facilityDB.setCampus(campus);
        }

        return this.facilityRepository.save(facilityDB);
    }

    public void deleteFacility(String id) {
        this.facilityRepository.deleteById(id);
    }
}
