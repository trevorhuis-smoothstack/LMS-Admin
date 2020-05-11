package com.ss.training.lms.service.admin;

import com.ss.training.lms.jdbc.ConnectionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ss.training.lms.dao.BookAuthorDAO;
import com.ss.training.lms.dao.BookDAO;
import com.ss.training.lms.dao.BookGenreDAO;
import com.ss.training.lms.entity.Author;
import com.ss.training.lms.entity.Book;
import com.ss.training.lms.entity.BookAuthor;
import com.ss.training.lms.entity.BookGenre;
import com.ss.training.lms.entity.Genre;

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
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void createBook(Book book) throws SQLException, ClassNotFoundException {
        Connection conn= null;
        boolean success= false;
        try {
            conn= connUtil.getConnection();
            book.setBookId(bookDAO.createBook(book, conn));
            success= true;
        } finally {
			if (success)
				conn.commit();
			else
				conn.rollback();
			if (conn != null)
				conn.close();
		}
    }

    /**
     * 
     * @param genreId
     * @param bookId
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void createGenreReference(Integer genreId, Integer bookId) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        boolean success = false;
        try {
            conn = connUtil.getConnection();
            BookGenre bookGenre = new BookGenre(genreId, bookId);
            bookGenreDAO.addBookGenreEntry(bookGenre, conn);
            success = true;
        } finally {
			if (success)
				conn.commit();
			else
				conn.rollback();
			if (conn != null)
				conn.close();
		}
    }

    /**
     * 
     * @param authorId
     * @param bookId
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void createAuthorReference(Integer authorId, Integer bookId) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        boolean success = false;
        try {
            conn = connUtil.getConnection();
            BookAuthor bookAuthor = new BookAuthor(bookId, authorId);
            bookAuthorDAO.addBookAuthorEntry(bookAuthor, conn);
            success = true;
        } finally {
			if (success)
				conn.commit();
			else
				conn.rollback();
			if (conn != null)
				conn.close();
		}
    }

    /**
     * 
     * @param book
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void deleteBook(Book book) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        boolean success = false;
        try {
            conn = connUtil.getConnection();
            bookDAO.deleteBook(book, conn);
            success = true;
        } finally {
			if (success)
				conn.commit();
			else
				conn.rollback();
			if (conn != null)
				conn.close();
		}
    }

    /**
     * 
     * @param book
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void updateBook(Book book) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        boolean success = false;
        try {
            conn = connUtil.getConnection();
            bookDAO.updateBook(book, conn);
            success= true;
        } finally {
			if (success)
				conn.commit();
			else
				conn.rollback();
			if (conn != null)
				conn.close();
		}
    }

    /**
     * 
     * @param bookId
     * @return
     * @throws SQLException
     */
    public Book readBook(Integer bookId) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            List<Book> books = bookDAO.readBookById(bookId, conn);
            if(books.size() == 0) {
                return null;
            }
            return books.get(0);
        } finally {
			if (conn != null) {
				conn.close();
			}
		}
    }

    /**
     * 
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public List<Book> readAllBooks() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            List<Book> books = bookDAO.readAllBooks(conn);
            if(books.size() == 0) {
                return null;
            }
            return books;
        } finally {
			if (conn != null) {
				conn.close();
			}
		}
    }
}