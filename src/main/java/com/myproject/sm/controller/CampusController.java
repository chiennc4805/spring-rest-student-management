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

import com.myproject.sm.domain.Campus;
import com.myproject.sm.domain.dto.response.ResultPaginationDTO;
import com.myproject.sm.service.CampusService;
import com.myproject.sm.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;

@RestController
public class CampusController {

    private final CampusService campusService;

    public CampusController(CampusService campusService) {
        this.campusService = campusService;
    }

    @PostMapping("/campus")
    public ResponseEntity<Campus> createCampus(@Valid @RequestBody Campus reqCampus) throws IdInvalidException {
        // check name exist
        boolean isExistByName = this.campusService.isExistByName(reqCampus.getName());
        if (isExistByName) {
            throw new IdInvalidException("Campus với tên = '" + reqCampus.getName() + "' đã tồn tại");
        }

        // create
        Campus newCampus = this.campusService.handleCreateCampus(reqCampus);

        return ResponseEntity.status(HttpStatus.CREATED).body(newCampus);
    }

    @GetMapping("/campus")
    public ResponseEntity<ResultPaginationDTO> getAllCampus(
            @Filter Specification<Campus> spec,
            Pageable pageable) {

        return ResponseEntity.ok(this.campusService.fetchAllCampus(spec, pageable));
    }

    @GetMapping("/campus/{id}")
    public ResponseEntity<Campus> getCampusById(@PathVariable("id") String id) throws IdInvalidException {
        // check id exist
        Campus subject = this.campusService.fetchCampusById(id);
        if (subject == null) {
            throw new IdInvalidException("Campus với id = " + id + " không tồn tại");
        }
        return ResponseEntity.ok(subject);
    }

    @PutMapping("/campus")
    public ResponseEntity<Campus> putCampus(@RequestBody Campus reqCampus) throws IdInvalidException {
        // check id exist
        Campus campusDB = this.campusService.fetchCampusById(reqCampus.getId());
        if (campusDB == null) {
            throw new IdInvalidException("Campus with id = " + reqCampus.getId() + " không tồn tại");
        }

        // check name exist
        boolean isExistByName = this.campusService.isExistByName(reqCampus.getName());
        if (isExistByName && !reqCampus.getName().equals(campusDB.getName())) {
            throw new IdInvalidException("Campus với tên = '" + reqCampus.getName() + "' đã tồn tại");
        }

        // update
        Campus updatedCampus = this.campusService.updateCampus(reqCampus);

        return ResponseEntity.ok(updatedCampus);
    }

    @DeleteMapping("/campus/{id}")
    public ResponseEntity<Void> deleteCampus(@PathVariable("id") String id) throws IdInvalidException {
        // check id exist
        Campus campusDB = this.campusService.fetchCampusById(id);
        if (campusDB == null) {
            throw new IdInvalidException("Campus with id = " + id + " không tồn tại");
        }

        // delete
        this.campusService.deleteCampus(id);
        return ResponseEntity.ok(null);
    }

}
