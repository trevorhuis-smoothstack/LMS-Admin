package com.ss.training.lms.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	/**
	 * @param branch
	 * @return
	 */
	@PutMapping(path = "/lms/admin/branch")
	public ResponseEntity<LibraryBranch> updateBranch(@RequestBody LibraryBranch branch) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		if (branch == null || (branch.getBranchName() != null && branch.getBranchName().length() > 45)
				|| (branch.getBranchAddress() != null && branch.getBranchAddress().length() > 45))
			return new ResponseEntity<LibraryBranch>(branch, status);
		try {
			if (service.readBranch(branch.getBranchId()) == null) // no branch with the specified ID exists
				return new ResponseEntity<LibraryBranch>(branch, status);
			service.updateBranch(branch);
			status = HttpStatus.OK;
		} catch (ClassNotFoundException | SQLException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<LibraryBranch>(branch, status);
	}

}
