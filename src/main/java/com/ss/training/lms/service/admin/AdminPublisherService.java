package com.ss.training.lms.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ss.training.lms.dao.BookDAO;
import com.ss.training.lms.dao.PublisherDAO;
import com.ss.training.lms.entity.Publisher;
import com.ss.training.lms.jdbc.ConnectionUtil;

@Component
public class AdminPublisherService {

	@Autowired
	ConnectionUtil connUtil;
	@Autowired
	PublisherDAO pubDAO;
	@Autowired
	BookDAO bookDAO;

	/**
	 * @param publisher
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void createPublisher(Publisher publisher) throws ClassNotFoundException, SQLException {
		boolean success = false;
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			// this will set the publisher ID in the calling class since objects are passed
			// by reference
			publisher.setPublisherID(pubDAO.addPublisher(publisher, conn));
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
	 * @param publisher
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void deletePublisher(Publisher publisher) throws ClassNotFoundException, SQLException {
		boolean success = false;
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			bookDAO.deleteBooksByPublisher(publisher.getPublisherId(), conn);
			pubDAO.deletePublisher(publisher, conn);
			success = true;
		} finally {
			if (success)
				conn.commit();
			else
				conn.rollback();
			if (conn != null)
				conn.close();
		}
	}

	public void updateAPublisher(Publisher publisher) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			pubDAO.updatePublisher(publisher, conn);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("We could not update that publisher.");
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	/**
	 * @param pubId
	 * @return
	 * @throws SQLException
	 */
	public Publisher readPublisher(Integer pubId) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			List<Publisher> publishers = pubDAO.readAPublisher(pubId, conn);
			if (publishers.size() == 0)
				return null;
			return publishers.get(0);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	public List<Publisher> readAllPublishers() throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			List<Publisher> publishers = pubDAO.readAllPublishers(conn);
			if (publishers.size() == 0) {
				return null;
			}
			return publishers;
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("We could not read the publishers.");
			conn.rollback();
			return null;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
}