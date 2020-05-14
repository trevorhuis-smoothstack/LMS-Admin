package com.ss.training.lms.dao;

import com.ss.training.lms.entity.LibraryBranch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryBranchDAO extends JpaRepository<LibraryBranch, Long> {
    LibraryBranch findByBranchId(Integer branchId);
}