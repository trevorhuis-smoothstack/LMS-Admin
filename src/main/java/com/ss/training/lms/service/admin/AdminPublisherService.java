package com.ss.training.lms.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ss.training.lms.dao.BookDAO;
import com.ss.training.lms.dao.PublisherDAO;
import com.ss.training.lms.entity.Book;
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
	 */
	public void savePublisher(Publisher publisher) {
		pubDAO.save(publisher);
	}

	/**
	 * @param publisher
	 */
	public void deletePublisher(Publisher publisher) {
		List<Book> books = bookDAO.findByPublisherId(publisher.getPublisherId());
		for(Book book : books) {
			bookDAO.delete(book);
		}
		pubDAO.delete(publisher);

	}


	/**
	 * @param pubId
	 * @return
	 */
	public Publisher readPublisher(Integer pubId) {
		return pubDAO.findByPublisherId(pubId);
	}

	/**
	 * @return
	 */
	public List<Publisher> readPublishers() {
		return pubDAO.findAll();
	}
}