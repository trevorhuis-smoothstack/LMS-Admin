package com.ss.training.lms.controller;

import java.sql.SQLException;
import java.util.List;

import com.ss.training.lms.entity.Book;
import com.ss.training.lms.entity.BookAuthor;
import com.ss.training.lms.entity.BookGenre;
import com.ss.training.lms.entity.Genre;
import com.ss.training.lms.service.admin.AdminAuthorService;
import com.ss.training.lms.service.admin.AdminBookService;
import com.ss.training.lms.service.admin.AdminGenreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Trevor Huis in 't Veld
 */
@RestController
@RequestMapping(path="lms/admin/book")
public class AdminBookController {

    @Autowired
    AdminBookService bookService;

    @Autowired
    AdminGenreService genreService;

    @Autowired
    AdminAuthorService authorService;

    /**
	 * @param book
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Book> createBook(@RequestBody Book book) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (book == null || book.getTitle() == null || book.getTitle().length() > 45 || book.getPublisherId() == null)
			return new ResponseEntity<Book>(book, status);
		try {
			bookService.createBook(book);
			status = HttpStatus.CREATED;
		} catch (ClassNotFoundException | SQLException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Book>(book, status);
    }

    /**
     * 
     * @param bookId
     * @return
     */
	@RequestMapping(path="/{id}")
	public ResponseEntity<Book> readBook(@PathVariable int bookId) {
		Book book = null;
		HttpStatus status = HttpStatus.OK;
		try {
			book = bookService.readBook(bookId);
			if (book == null) 
				status = HttpStatus.NOT_FOUND;
		} catch ( SQLException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Book>(book, status);
	}

    /**
     * 
     * @return
     */
	@RequestMapping
	public ResponseEntity<List<Book>> readBooks() {
		List<Book> books = null;
		HttpStatus status = HttpStatus.OK;
		try {
			books = bookService.readAllBooks();
			if (books == null) // no Books exist in the database
				status = HttpStatus.NO_CONTENT;
		} catch (ClassNotFoundException | SQLException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<List<Book>>(books, status);
	}

    /**
     * 
     * @param bookId
     * @return
     */
	@DeleteMapping(path="/{id}")
	public ResponseEntity<Book> deleteBook(@PathVariable int bookId) {
		Book book = null;
		HttpStatus status = HttpStatus.OK;
		try {
			book = bookService.readBook(bookId);
			if (book == null) 
				status = HttpStatus.NOT_FOUND;
			else
				bookService.deleteBook(book);
		} catch (ClassNotFoundException | SQLException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Book>(book, status);
	}

    /**
     * 
     * @param book
     * @return
     */
	@PutMapping
	public ResponseEntity<Book> updateBook(@RequestBody Book book) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		if (book == null || book.getTitle().length() > 45)
			return new ResponseEntity<Book>(book, status);
		try {
			if (bookService.readBook(book.getBookId()) == null)
				return new ResponseEntity<Book>(book, HttpStatus.NOT_FOUND);
			bookService.updateBook(book);
			status = HttpStatus.OK;
		} catch (ClassNotFoundException | SQLException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Book>(book, status);
    }
    
    // @PostMapping(path="/genre")
    // public ResponseEntity<BookGenre> createGenreReference(@RequestBody BookGenre genreRef) {
    //     HttpStatus status = HttpStatus.BAD_REQUEST;
	// 	if (genreRef == null || genreRef.getBookId() == null || genreRef.getGenreId() == null)
    //         return new ResponseEntity<BookGenre>(genreRef, status);
    //     try {
    //         Genre genre = genreService.readGenre(genreRef.getGenreId());
    //         Author author = authorService.readAnAuthor(authorId)
    //         bookService.createGenreReference(genreRef.getGenreId(), genreRef.getBookId());
    //         status = HttpStatus.CREATED;
    //     } catch (ClassNotFoundException | SQLException e) {
    //         status = HttpStatus.INTERNAL_SERVER_ERROR;
    //     }
    //     return new ResponseEntity<BookGenre>(genreRef, status);
    // }

    // @PostMapping(path="/author")
    // public ResponseEntity<BookGenre> createGenreReference(@RequestBody BookAuthor authorRef) {
    //     HttpStatus status = HttpStatus.BAD_REQUEST;
	// 	if (genreRef == null || genreRef.getBookId() == null || genreRef.getGenreId() == null)
    //         return new ResponseEntity<BookGenre>(genreRef, status);
            
    //     return new ResponseEntity<BookGenre>(genreRef, status);
    // }
}