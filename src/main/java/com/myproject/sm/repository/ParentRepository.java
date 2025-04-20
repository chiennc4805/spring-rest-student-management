package com.myproject.sm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.myproject.sm.domain.Parent;

@Repository
public interface ParentRepository extends JpaRepository<Parent, String>, JpaSpecificationExecutor<Parent> {

}
