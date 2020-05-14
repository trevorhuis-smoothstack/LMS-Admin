package com.ss.training.lms.controller;

import java.sql.SQLException;

import com.ss.training.lms.entity.BookLoan;
import com.ss.training.lms.service.admin.AdminOverrideLoanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminOverrideLoanController
{
    @Autowired
    AdminOverrideLoanService adminOverrideLoanService;

	/**
	 * 
	 * @param cardNo
	 * @return
	 * @throws SQLException
	 */
	@PutMapping(path="/lms/admin/bookloans/extend")
	public ResponseEntity<BookLoan> addAWeekToALoan(@RequestBody BookLoan bookLoan)  {

		System.out.println("hello");
		HttpStatus status = HttpStatus.OK;
		if (bookLoan == null || bookLoan.getDateOut() == null || bookLoan.getCardNo() == null || bookLoan.getBookId() == null)
		{
			status = HttpStatus.BAD_REQUEST;
			return new ResponseEntity<BookLoan>(bookLoan, status);
		}

		boolean updated = adminOverrideLoanService.addAWeekToALoan(bookLoan);
		if (updated == false)
		{
			status = HttpStatus.BAD_REQUEST;
			return new ResponseEntity<BookLoan>(bookLoan, status);
		}
		return new ResponseEntity<BookLoan>(bookLoan, status);
	}
}