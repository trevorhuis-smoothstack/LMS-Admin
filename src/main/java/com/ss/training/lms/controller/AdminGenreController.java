package com.ss.training.lms.controller;

import java.sql.SQLException;

import com.ss.training.lms.entity.Genre;
import com.ss.training.lms.service.admin.AdminGenreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AdminGenreController {
	
	@Autowired
	AdminGenreService genreService;
	
	@RequestMapping(path="/lms/admin/genre/{genreId}", 
					produces = {
						MediaType.APPLICATION_XML_VALUE,
						MediaType.APPLICATION_JSON_VALUE
					})
	public ResponseEntity<Genre>getGenreById(@PathVariable int genreId) throws SQLException {
		Genre genre= genreService.readAGenre(genreId);
		return new ResponseEntity<Genre>(genre, HttpStatus.CREATED);
	}
	
	@RequestMapping(path="/lms/admin/genre",
					method = RequestMethod.POST,
					consumes = {
						MediaType.APPLICATION_JSON_VALUE,
						MediaType.APPLICATION_XML_VALUE
					})
	public void addGenre(@RequestBody Genre genre) throws SQLException {
		Integer pk = genreService.addAGenre(genre);
		System.out.println(pk);
	}

	@RequestMapping(path="/lms/admin/genre",
					method = RequestMethod.PUT,
					consumes = {
						MediaType.APPLICATION_JSON_VALUE,
						MediaType.APPLICATION_XML_VALUE
					})
	public void updateGenre(@RequestBody Genre genre) throws SQLException {
		genreService.updateAGenre(genre);
	}

	@RequestMapping(path="/lms/admin/genre",
					method = RequestMethod.DELETE,
					consumes = {
						MediaType.APPLICATION_JSON_VALUE,
						MediaType.APPLICATION_XML_VALUE
					})
	public void deleteGenre(@RequestBody Genre genre) throws SQLException {
		genreService.deleteAGenre(genre);
	}


}
