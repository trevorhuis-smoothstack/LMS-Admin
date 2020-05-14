package com.ss.training.lms.dao;

import java.util.List;

import com.ss.training.lms.entity.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookDAO extends JpaRepository<Book, Long> {
    Book findByBookId(Integer bookId);

    List<Book> findByPublisherId(Integer publisherId);
}