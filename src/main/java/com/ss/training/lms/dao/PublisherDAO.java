package com.ss.training.lms.dao;

import com.ss.training.lms.entity.Publisher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherDAO extends JpaRepository<Publisher, Long> {
    Publisher findByPublisherId(Integer publisherId);
}