package com.ss.training.lms.service.admin;

import com.ss.training.lms.jdbc.ConnectionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import com.ss.training.lms.dao.BookAuthorDAO;
import com.ss.training.lms.dao.BookDAO;
import com.ss.training.lms.dao.BookGenreDAO;
import com.ss.training.lms.entity.Book;
import com.ss.training.lms.entity.BookAuthor;
import com.ss.training.lms.entity.BookGenre;

/**
 * @author Trevor Huis in 't Veld
 */
@Component
public class AdminBookService {
    @Autowired
    ConnectionUtil connUtil;

    @Autowired
    BookDAO bookDAO;

    @Autowired
    BookGenreDAO bookGenreDAO;

    @Autowired
    BookAuthorDAO bookAuthorDAO;
    
    /**
     * 
     * @param book
     */
    public void saveBook(Book book) {
        bookDAO.save(book);
    }

    /**
     * 
     * @param bookGenre
     */
    public void createGenreReference(BookGenre genreRef) {
        bookGenreDAO.save(genreRef);
    }

    /**
     * 
     * @param authorRef
     */
    public void createAuthorReference(BookAuthor authorRef) {
        bookAuthorDAO.save(authorRef);
    }

    /**
     * 
     * @param book
     */
    public void deleteBook(Book book) {
        bookDAO.delete(book);
    }

    /**
     * 
     * @param bookId
     * @return
     */
    public Book readBook(Integer bookId) {
        return bookDAO.findByBookId(bookId);
    }

    /**
     * 
     * @return
     */
    public List<Book> readAllBooks() {
        return bookDAO.findAll();
    }
}