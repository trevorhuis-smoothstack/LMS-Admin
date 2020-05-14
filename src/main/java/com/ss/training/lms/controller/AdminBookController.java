package com.ss.training.lms.controller;


import java.util.List;

import com.ss.training.lms.entity.Author;
import com.ss.training.lms.entity.Book;
import com.ss.training.lms.entity.BookAuthor;
import com.ss.training.lms.entity.BookGenre;
import com.ss.training.lms.entity.Genre;
import com.ss.training.lms.service.admin.AdminAuthorService;
import com.ss.training.lms.service.admin.AdminBookService;
import com.ss.training.lms.service.admin.AdminGenreService;

import org.springframework.beans.factory.annotation.Autowired;
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
        if (book == null || book.getTitle() == null || (book.getTitle() != null && book.getTitle().length() > 45))
			return new ResponseEntity<Book>(book, status);
		
		bookService.saveBook(book);
		status = HttpStatus.CREATED;
		return new ResponseEntity<Book>(book, status);
    }

    /**
     * 
     * @param bookId
     * @return
     */
	@RequestMapping(path="/{bookId}")
	public ResponseEntity<Book> readBook(@PathVariable int bookId) {
		Book book = null;
		HttpStatus status = HttpStatus.OK;
		
		book = bookService.readBook(bookId);
		if (book == null) 
			status = HttpStatus.NOT_FOUND;
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
		
		books = bookService.readAllBooks();
		if (books == null) // no Books exist in the database
			status = HttpStatus.NO_CONTENT;
		return new ResponseEntity<List<Book>>(books, status);
	}

    /**
     * 
     * @param bookId
     * @return
     */
	@DeleteMapping(path="/{bookId}")
	public ResponseEntity<Book> deleteBook(@PathVariable int bookId) {
		Book book = null;
		HttpStatus status = HttpStatus.OK;
		
		book = bookService.readBook(bookId);
		if (book == null) 
			status = HttpStatus.NOT_FOUND;
		else
			bookService.deleteBook(book);

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
		if (book == null || (book.getTitle() != null && book.getTitle().length() > 45))
			return new ResponseEntity<Book>(book, status);

		if (bookService.readBook(book.getBookId()) == null)
			return new ResponseEntity<Book>(book, HttpStatus.NOT_FOUND);
		bookService.saveBook(book);
		status = HttpStatus.OK;
		return new ResponseEntity<Book>(book, status);
    }
	
	/**
	 * 
	 * @param genreRef
	 * @return
	 */
    @PostMapping(path="/genre")
    public ResponseEntity<BookGenre> createGenreReference(@RequestBody BookGenre genreRef) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
		if (genreRef == null || genreRef.getBookId() == null || genreRef.getGenreId() == null)
            return new ResponseEntity<BookGenre>(genreRef, status);
        
		Genre genre = genreService.readGenre(genreRef.getGenreId());
		Book book = bookService.readBook(genreRef.getBookId());
		
		if(book == null || genre == null) // Both need to exist to create a reference
			return new ResponseEntity<BookGenre>(genreRef, status);
		bookService.createGenreReference(genreRef);
		status = HttpStatus.CREATED;
        return new ResponseEntity<BookGenre>(genreRef, status);
    }

	/**
	 * 
	 * @param authorRef
	 * @return
	 */
    @PostMapping(path="/author")
    public ResponseEntity<BookAuthor> createGenreReference(@RequestBody BookAuthor authorRef) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
		if (authorRef == null || authorRef.getBookId() == null || authorRef.getAuthorId() == null)
			return new ResponseEntity<BookAuthor>(authorRef, status);
		
		Author author = authorService.readAuthor(authorRef.getAuthorId());
		Book book = bookService.readBook(authorRef.getBookId());

		if(book == null || author == null) // Both need to exist to create a reference
			return new ResponseEntity<BookAuthor>(authorRef, status);
		bookService.createAuthorReference(authorRef);
		status = HttpStatus.CREATED;
        return new ResponseEntity<BookAuthor>(authorRef, status);
    }
}