package com.ss.training.lms.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.training.lms.entity.LibraryBranch;
import com.ss.training.lms.service.admin.AdminBranchService;

/**
 * @author Justin O'Brien
 */
@RestController
public class AdminBranchController {

	@Autowired
	AdminBranchService service;

	@RequestMapping(path = "/lms/admin/branches/{id}")
	public ResponseEntity<LibraryBranch> readBranch(@PathVariable int id) {
		LibraryBranch branch = null;
		HttpStatus status = HttpStatus.OK;
		try {
			branch = service.readBranch(id);
			if (branch == null) // no branch with the requested ID exists
				status = HttpStatus.NOT_FOUND;
		} catch (SQLException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<LibraryBranch>(branch, status);
	}

}
