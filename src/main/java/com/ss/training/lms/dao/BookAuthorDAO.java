package com.ss.training.lms.dao;

import com.ss.training.lms.entity.BookAuthor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookAuthorDAO extends JpaRepository<BookAuthor, Long> {

}