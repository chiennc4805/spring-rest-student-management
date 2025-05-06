package com.myproject.sm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.myproject.sm.domain.Class;

@Repository
public interface ClassRepository extends JpaRepository<Class, String>, JpaSpecificationExecutor<Class> {

    boolean existsByName(String name);

    Optional<Class> findByName(String name);
}
