package com.myproject.sm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.myproject.sm.domain.StudentFee;

@Repository
public interface StudentFeeRepository extends JpaRepository<StudentFee, String>, JpaSpecificationExecutor<StudentFee> {

}
