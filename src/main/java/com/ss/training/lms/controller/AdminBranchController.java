package com.ss.training.lms.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	/**
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

}
