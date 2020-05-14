package com.ss.training.lms.dao;

import com.ss.training.lms.entity.Genre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreDAO extends JpaRepository<Genre, Long> {

}