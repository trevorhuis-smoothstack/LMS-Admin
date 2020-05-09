package com.ss.training.lms.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.training.lms.entity.Publisher;
import com.ss.training.lms.service.admin.AdminPublisherService;

/**
 * @author Justin O'Brien
 */
@RestController
public class AdminPublisherController {

	@Autowired
	AdminPublisherService service;

	/**
	 * @param id
	 * @return
	 */
	@RequestMapping(path = "/lms/admin/publishers/{id}")
	public ResponseEntity<Publisher> readPublisher(@PathVariable int id) {
		Publisher publisher = null;
		HttpStatus status = HttpStatus.OK;
		try {
			publisher = service.readPublisher(id);
			if (publisher == null) // no publisher with the requested ID exists
				status = HttpStatus.NOT_FOUND;
		} catch (SQLException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Publisher>(publisher, status);
	}

	/**
	 * @param publisher
	 * @return
	 */
	@PutMapping(path = "/lms/admin/publisher")
	public ResponseEntity<Publisher> updatePublisher(@RequestBody Publisher publisher) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		if (publisher == null || publisher.getPublisherName() == null || publisher.getPublisherName().length() > 45
				|| (publisher.getAddress() != null && publisher.getAddress().length() > 45)
				|| (publisher.getPhone() != null && publisher.getPhone().length() > 45))
			return new ResponseEntity<Publisher>(publisher, status);
		try {
			if (service.readPublisher(publisher.getPublisherId()) == null) // no publisher with the specified ID exists
				return new ResponseEntity<Publisher>(publisher, status);
			service.updatePublisher(publisher);
			status = HttpStatus.OK;
		} catch (ClassNotFoundException | SQLException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Publisher>(publisher, status);
	}

}
