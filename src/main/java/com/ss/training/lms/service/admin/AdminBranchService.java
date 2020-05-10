package com.ss.training.lms.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ss.training.lms.dao.BookCopiesDAO;
import com.ss.training.lms.dao.BookLoanDAO;
import com.ss.training.lms.dao.LibraryBranchDAO;
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
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void createBranch(LibraryBranch branch) throws ClassNotFoundException, SQLException {
		boolean success = false;
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			// this will also set the branch ID in the calling context, since objects are
			// passed by reference
			branch.setBranchId(branchDAO.addBranch(branch, conn));
			success = true;
		} finally {
			if (success)
				conn.commit();
			else
				conn.rollback();
			if (conn != null) {
				conn.close();
			}
		}
	}

	/**
	 * @param branch
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void deleteBranch(LibraryBranch branch) throws ClassNotFoundException, SQLException {
		boolean success = false;
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			loanDAO.deleteBookLoansByBranch(branch.getBranchId(), conn);
			copiesDAO.deleteBookLoansByBranch(branch.getBranchId(), conn);
			branchDAO.deleteBranch(branch, conn);
			success = true;
		} finally {
			if (success)
				conn.commit();
			else
				conn.rollback();
			if (conn != null) {
				conn.close();
			}
		}
	}

	/**
	 * @param branch
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void updateBranch(LibraryBranch branch) throws ClassNotFoundException, SQLException {
		boolean success = false;
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			branchDAO.updateBranch(branch, conn);
			success = true;
		} finally {
			if (success)
				conn.commit();
			else
				conn.rollback();
			if (conn != null) {
				conn.close();
			}
		}
	}

	/**
	 * @param branchId
	 * @return
	 * @throws SQLException
	 */
	public LibraryBranch readBranch(Integer branchId) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			List<LibraryBranch> branches = branchDAO.readABranch(branchId, conn);
			if (branches.size() == 0) {
				return null;
			}
			return branches.get(0);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	/**
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<LibraryBranch> readBranches() throws ClassNotFoundException, SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			List<LibraryBranch> authors = branchDAO.readAllBranches(conn);
			if (authors.size() == 0) {
				return null;
			}
			return authors;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
}