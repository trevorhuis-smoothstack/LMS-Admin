package com.ss.training.lms.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public ResponseEntity<LibraryBranch> createBranch(@RequestBody LibraryBranch branch) {
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

	/**
	 * <<<<<<< HEAD
	 * 
	 * @return
	 */
	@RequestMapping(path = "/lms/admin/branch")
	public ResponseEntity<List<LibraryBranch>> readBranches() {
		List<LibraryBranch> branches = null;
		HttpStatus status = HttpStatus.OK;
		try {
			branches = service.readBranches();
			if (branches == null) // no branches exist in the database
				status = HttpStatus.NO_CONTENT;
		} catch (ClassNotFoundException | SQLException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<List<LibraryBranch>>(branches, status);
	}

	/**
	 * @param id
	 * @return
	 */
	@DeleteMapping(path = "/lms/admin/branches/{id}")
	public ResponseEntity<LibraryBranch> deleteBranch(@PathVariable int id) {
		LibraryBranch branch = null;
		HttpStatus status = HttpStatus.OK;
		try {
			branch = service.readBranch(id);
			if (branch == null) // no branch with the specified ID exists
				status = HttpStatus.NOT_FOUND;
			else
				service.deleteBranch(branch);
		} catch (ClassNotFoundException | SQLException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<LibraryBranch>(branch, status);
	}

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

	/**
	 * * @param branch
	 * 
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
