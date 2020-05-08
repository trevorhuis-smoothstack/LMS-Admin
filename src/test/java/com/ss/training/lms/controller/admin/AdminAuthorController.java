package com.ss.training.lms.controller.admin;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
	 * @param id
	 * @return
	 */
	@RequestMapping(path = "lms/authors/{id}")
	public ResponseEntity<Author> readAuthor(@PathVariable int id) {
		Author author = null;
		try {
			author = service.readAuthor(id);

		} catch (ClassNotFoundException | SQLException e) {
			return new ResponseEntity<Author>(author, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (author == null) // no author with the requested ID exists
			return new ResponseEntity<Author>(author, HttpStatus.NOT_FOUND);
		return new ResponseEntity<Author>(author, HttpStatus.OK);
	}

}
