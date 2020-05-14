package com.ss.training.lms.service.admin;

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

	/**
	 * @param author
	 */
	public void saveAuthor(Author author) {
		authorDAO.save(author);
	}

	/**
	 * @param author
	 */
	public void deleteAuthor(Author author) {
		authorDAO.delete(author);
	}

	/**
	 * @param authorId
	 * @return
	 */
	public Author readAuthor(Integer authorId) {
		return authorDAO.findByAuthorId(authorId);
	}

	/**
	 * @return
	 */
	public List<Author> readAuthors() {
		return authorDAO.findAll();
	}
}