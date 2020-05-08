package com.ss.training.lms.controller;

import java.sql.SQLException;
import java.util.List;

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

/**
 * @author Trevor Huis in 't Veld
 */
@RestController
public class AdminGenreController {
	
	@Autowired
	AdminGenreService genreService;
	
	@RequestMapping(path="/lms/admin/genre/{genreId}", 
					produces = {
						MediaType.APPLICATION_XML_VALUE,
						MediaType.APPLICATION_JSON_VALUE
					})
	public ResponseEntity<Genre>getGenreById(@PathVariable int genreId) {
		Genre genre = null;
		HttpStatus status = HttpStatus.OK;
		try {
			genre= genreService.readGenre(genreId);
			if (genre == null) {
				status = HttpStatus.NOT_FOUND;
			}
		} catch (SQLException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Genre>(genre, status);
	}

	@RequestMapping(path="/lms/admin/genres", 
					produces = {
						MediaType.APPLICATION_XML_VALUE,
						MediaType.APPLICATION_JSON_VALUE
					})
	public ResponseEntity<List<Genre>> getAllGenres() {
		List<Genre> genres = null;
		HttpStatus status = HttpStatus.OK;
        try {
			genres= genreService.readAllGenres();
			if (genres == null) {
				status = HttpStatus.NOT_FOUND;
			}
		} catch (ClassNotFoundException | SQLException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
        return new ResponseEntity<List<Genre>>(genres, status);
	}

	
	@RequestMapping(path="/lms/admin/genre",
					method = RequestMethod.POST,
					consumes = {
						MediaType.APPLICATION_JSON_VALUE,
						MediaType.APPLICATION_XML_VALUE
					})
	public ResponseEntity<Genre> addGenre(@RequestBody Genre genre) {
		if(genre.getGenreName() == null)
				return new ResponseEntity<Genre>(genre, HttpStatus.BAD_REQUEST);
		HttpStatus status = HttpStatus.OK;
        try {
			genreService.addGenre(genre);
		} catch (ClassNotFoundException | SQLException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
        return new ResponseEntity<Genre>(genre, status);
		
	}

	@RequestMapping(path="/lms/admin/genre",
					method = RequestMethod.PUT,
					consumes = {
						MediaType.APPLICATION_JSON_VALUE,
						MediaType.APPLICATION_XML_VALUE
					})
	public ResponseEntity<Genre> updateGenre(@RequestBody Genre genre) {
		if(genre.getGenreName() == null || genre.getGenreID() == null)
			return new ResponseEntity<Genre>(genre, HttpStatus.BAD_REQUEST);
		HttpStatus status = HttpStatus.OK;
        try {
			genreService.updateGenre(genre);
		} catch (ClassNotFoundException | SQLException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
        return new ResponseEntity<Genre>(genre, status);
		
		
	}

	@RequestMapping(path="/lms/admin/genre",
					method = RequestMethod.DELETE,
					consumes = {
						MediaType.APPLICATION_JSON_VALUE,
						MediaType.APPLICATION_XML_VALUE
					})
	public ResponseEntity<Genre> deleteGenre(@RequestBody Genre genre) {
		if(genre.getGenreName() == null || genre.getGenreID() == null)
			return new ResponseEntity<Genre>(genre, HttpStatus.BAD_REQUEST);
		HttpStatus status = HttpStatus.OK;
        try {
			genreService.deleteGenre(genre);
		} catch (ClassNotFoundException | SQLException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
        return new ResponseEntity<Genre>(genre, status);
	}
}
