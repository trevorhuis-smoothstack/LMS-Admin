package com.ss.training.lms.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ss.training.lms.entity.LibraryBranch;
import com.ss.training.lms.service.admin.AdminBranchService;

/**
 * @author Justin O'Brien
 */
@RestController
public class AdminBranchController {

	/**
	 * @param branch
	 * @return
	 */
	@PostMapping(path = "/lms/admin/branch")
	public ResponseEntity<LibraryBranch> createLibraryBranch(@RequestBody LibraryBranch branch) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		if (branch == null || (branch.getBranchName() != null && branch.getBranchName().length() > 45)
				|| (branch.getBranchAddress() != null && branch.getBranchAddress().length() > 45))
			return new ResponseEntity<LibraryBranch>(branch, status);
		try {
			service.createBranch(branch); // this will also set the ID of this branch object if successful
			status = HttpStatus.CREATED;
		} catch (ClassNotFoundException | SQLException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<LibraryBranch>(branch, status);
	}

	@Autowired
	AdminBranchService service;

}
