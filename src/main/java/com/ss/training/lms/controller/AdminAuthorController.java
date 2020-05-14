package com.ss.training.lms.controller;

import java.util.List;

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

import com.ss.training.lms.entity.Author;
import com.ss.training.lms.service.admin.AdminAuthorService;

/**
 * @author Justin O'Brien
 */
@RestController
public class AdminAuthorController {

	@Autowired
	AdminAuthorService service;

	/**
	 * @param author
	 * @return
	 */
	@PostMapping(path = "/lms/admin/author")
	public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		if (author == null || author.getAuthorName() == null || author.getAuthorName().length() > 45)
			return new ResponseEntity<Author>(author, status);

		service.saveAuthor(author); // this will also set the ID of this author object if successful
		status = HttpStatus.CREATED;

		return new ResponseEntity<Author>(author, status);
	}

	/**
	 * @param id
	 * @return
	 */
	@RequestMapping(path = "/lms/admin/authors/{id}")
	public ResponseEntity<Author> readAuthor(@PathVariable int id) {
		Author author = null;
		HttpStatus status = HttpStatus.OK;

		author = service.readAuthor(id);
		if (author == null) // no author with the specified ID exists
			status = HttpStatus.NOT_FOUND;

		return new ResponseEntity<Author>(author, status);
	}

	/**
	 * @return
	 */
	@RequestMapping(path = "/lms/admin/authors")
	public ResponseEntity<Author[]> readAuthors() {
		List<Author> authorList = null;
		Author[] authorArray = null;
		HttpStatus status = HttpStatus.OK;
		authorList = service.readAuthors();
		if (authorList == null) // no authors exist in the database
			status = HttpStatus.NO_CONTENT;
		else
			authorArray = authorList.toArray(new Author[authorList.size()]);
		return new ResponseEntity<Author[]>(authorArray, status);
	}

	/**
	 * @param id
	 * @return
	 */
	@DeleteMapping(path = "/lms/admin/authors/{id}")
	public ResponseEntity<Author> deleteAuthor(@PathVariable int id) {
		Author author = null;
		HttpStatus status = HttpStatus.OK;

		author = service.readAuthor(id);
		if (author == null) // no author with the specified ID exists
			status = HttpStatus.NOT_FOUND;
		else
			service.deleteAuthor(author);

		return new ResponseEntity<Author>(author, status);
	}

	/**
	 * @param author
	 * @return
	 */
	@PutMapping(path = "/lms/admin/author")
	public ResponseEntity<Author> updateAuthor(@RequestBody Author author) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		if (author == null || author.getAuthorName() == null || author.getAuthorName().length() > 45)
			return new ResponseEntity<Author>(author, status);

		if (service.readAuthor(author.getAuthorId()) == null) // no author with the specified ID exists
			return new ResponseEntity<Author>(author, HttpStatus.NOT_FOUND);
		service.saveAuthor(author);
		status = HttpStatus.OK;

		return new ResponseEntity<Author>(author, status);
	}

}
