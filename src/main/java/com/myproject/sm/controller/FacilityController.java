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

import com.myproject.sm.domain.Facility;
import com.myproject.sm.domain.response.ResultPaginationDTO;
import com.myproject.sm.service.FacilityService;
import com.myproject.sm.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;

@RestController
public class FacilityController {

    private final FacilityService facilityService;

    public FacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @PostMapping("/facilities")
    public ResponseEntity<Facility> createFacility(@Valid @RequestBody Facility reqFacility) {
        Facility newFacility = this.facilityService.handleCreateFacility(reqFacility);

        return ResponseEntity.status(HttpStatus.CREATED).body(newFacility);
    }

    @GetMapping("/facilities")
    public ResponseEntity<ResultPaginationDTO> fetchAllFacilities(
            @Filter Specification<Facility> spec,
            Pageable pageable) {

        return ResponseEntity.ok(this.facilityService.fetchAllFacilities(spec, pageable));
    }

    @GetMapping("/facilities/{id}")
    public ResponseEntity<Facility> fetchFacilityById(@PathVariable("id") String id) throws IdInvalidException {
        Facility facility = this.facilityService.fetchFacilityById(id);
        if (facility == null) {
            throw new IdInvalidException("Facility with id = " + id + " không tồn tại");
        }
        return ResponseEntity.ok(facility);
    }

    @PutMapping("/facilities")
    public ResponseEntity<Facility> updateStudent(@RequestBody Facility reqFacility) throws IdInvalidException {
        Facility facilityDB = this.facilityService.fetchFacilityById(reqFacility.getId());
        if (facilityDB == null) {
            throw new IdInvalidException("Facility with id = " + reqFacility.getId() + " không tồn tại");
        }
        Facility updatedStudent = this.facilityService.updateFacility(reqFacility);

        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/facilities/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") String id) throws IdInvalidException {
        Facility facilityDB = this.facilityService.fetchFacilityById(id);
        if (facilityDB == null) {
            throw new IdInvalidException("Facility with id = " + id + " không tồn tại");
        }
        this.facilityService.deleteFacility(id);
        return ResponseEntity.ok(null);
    }

}
