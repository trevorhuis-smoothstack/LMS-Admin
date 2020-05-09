package com.ss.training.lms.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	@RequestMapping(path = "/lms/admin/authors/{id}")
	public ResponseEntity<Author> readAuthor(@PathVariable int id) {
		Author author = null;
		HttpStatus status = HttpStatus.OK;
		try {
			author = service.readAuthor(id);
			if (author == null) // no author with the requested ID exists
				status = HttpStatus.NOT_FOUND;
		} catch (ClassNotFoundException | SQLException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Author>(author, status);
	}

	@DeleteMapping(path = "/lms/admin/authors{id}")
	public ResponseEntity<Author> deleteAuthor(@PathVariable int id) {
		return null;
	}

}
