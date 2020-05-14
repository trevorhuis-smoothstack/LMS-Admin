package com.ss.training.lms.dao;

import com.ss.training.lms.entity.BookGenre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookGenreDAO extends JpaRepository<BookGenre, Long> {

}