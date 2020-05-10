package com.ss.training.lms.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ss.training.lms.dao.AuthorDAO;
import com.ss.training.lms.entity.Author;
import com.ss.training.lms.jdbc.ConnectionUtil;

@Component
public class AdminAuthorService {

	@Autowired
	ConnectionUtil connUtil;
	@Autowired
	AuthorDAO authorDAO;

	public Integer addAuthor(Author author) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			Integer primaryKey = authorDAO.addAuthor(author, conn);
			conn.commit();
			return primaryKey;
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("We could not add that author.");
			e.printStackTrace();
			conn.rollback();
			return 0;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	public void deleteAuthor(Author author) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			authorDAO.deleteAuthor(author, conn);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("We could not delete that author.");
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	public void updateAuthor(Author author) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			authorDAO.updateAuthor(author, conn);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("We could not update that author.");
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	/**
	 * @param authorId
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Author readAuthor(Integer authorId) throws ClassNotFoundException, SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			List<Author> authors = authorDAO.readAnAuthor(authorId, conn);
			if (authors.size() == 0)
				return null;
			return authors.get(0);
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
	public List<Author> readAuthors() throws ClassNotFoundException, SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			List<Author> authors = authorDAO.readAllAuthors(conn);
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