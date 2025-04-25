package com.myproject.sm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.myproject.sm.domain.Campus;

@Repository
public interface CampusRepository extends JpaRepository<Campus, String>, JpaSpecificationExecutor<Campus> {

    boolean existsByName(String name);

}
