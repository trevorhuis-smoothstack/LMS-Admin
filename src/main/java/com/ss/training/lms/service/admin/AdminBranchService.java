package com.ss.training.lms.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ss.training.lms.dao.BookCopiesDAO;
import com.ss.training.lms.dao.BookLoanDAO;
import com.ss.training.lms.dao.LibraryBranchDAO;
import com.ss.training.lms.entity.BookCopies;
import com.ss.training.lms.entity.BookLoan;
import com.ss.training.lms.entity.LibraryBranch;
import com.ss.training.lms.jdbc.ConnectionUtil;

@Component
public class AdminBranchService {

	@Autowired
	ConnectionUtil connUtil;
	@Autowired
	LibraryBranchDAO branchDAO;
	@Autowired
	BookCopiesDAO copiesDAO;
	@Autowired
	BookLoanDAO loanDAO;

	/**
	 * @param branch
	 */
	public void saveBranch(LibraryBranch branch) {
		branchDAO.save(branch);
	}

	/**
	 * @param branch
	 */
	public void deleteBranch(LibraryBranch branch) {
		List<BookLoan> loans = loanDAO.findByBranchId(branch.getBranchId());
		List<BookCopies> copies = copiesDAO.findByBranchId(branch.getBranchId());
		for(BookLoan loan: loans) {
			loanDAO.delete(loan);
		}
		for(BookCopies copy: copies) {
			copiesDAO.delete(copy);
		}
		branchDAO.delete(branch);
	}

	/**
	 * @param branchId
	 * @return
	 */
	public LibraryBranch readBranch(Integer branchId) {
		return branchDAO.findByBranchId(branchId);
	}

	/**
	 * @return
	 */
	public List<LibraryBranch> readBranches() {
		return branchDAO.findAll();
	}
}