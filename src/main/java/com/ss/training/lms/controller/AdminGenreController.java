package com.ss.training.lms.controller;

import java.sql.SQLException;
import java.util.List;

import com.ss.training.lms.entity.Genre;
import com.ss.training.lms.service.admin.AdminGenreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Trevor Huis in 't Veld
 */
@RestController
@RequestMapping("/lms/admin/genre")
public class AdminGenreController {
	
	@Autowired
	AdminGenreService genreService;
	
	/**
	 * 
	 * @param genreId
	 * @return
	 */
	@GetMapping(path="/{genreId}", 
					produces = {
						MediaType.APPLICATION_XML_VALUE,
						MediaType.APPLICATION_JSON_VALUE
					})
	public ResponseEntity<Genre>readGenre(@PathVariable int genreId) {
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

	/**
	 * 
	 * @return
	 */
	@GetMapping(produces = {
						MediaType.APPLICATION_XML_VALUE,
						MediaType.APPLICATION_JSON_VALUE
					})
	public ResponseEntity<List<Genre>> readGenres() {
		List<Genre> genres = null;
		HttpStatus status = HttpStatus.OK;
        try {
			genres= genreService.readAllGenres();
			if (genres == null) {
				status = HttpStatus.NO_CONTENT;
			}
		} catch (ClassNotFoundException | SQLException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
        return new ResponseEntity<List<Genre>>(genres, status);
	}

	/**
	 * 
	 * @param genre
	 * @return
	 */
	@PostMapping(consumes = {
						MediaType.APPLICATION_JSON_VALUE,
						MediaType.APPLICATION_XML_VALUE
					})
	public ResponseEntity<Genre> createGenre(@RequestBody Genre genre) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		if(genre == null || genre.getGenreName().length() > 45)
				return new ResponseEntity<Genre>(genre, HttpStatus.BAD_REQUEST);
        try {
			genreService.createGenre(genre);
			status = HttpStatus.CREATED;
		} catch (ClassNotFoundException | SQLException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
        return new ResponseEntity<Genre>(genre, status);
		
	}

	/**
	 * 
	 * @param genre
	 * @return
	 */
	@PutMapping(consumes = {
						MediaType.APPLICATION_JSON_VALUE,
						MediaType.APPLICATION_XML_VALUE
					})
	public ResponseEntity<Genre> updateGenre(@RequestBody Genre genre) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		if(genre == null || genre.getGenreId() == null || genre.getGenreName().length() > 45)
			return new ResponseEntity<Genre>(genre, status);
        try {
			if(genreService.readGenre(genre.getGenreId()) == null)
				return new ResponseEntity<Genre>(genre, HttpStatus.NOT_FOUND);
			genreService.updateGenre(genre);
			status = HttpStatus.CREATED;
		} catch (ClassNotFoundException | SQLException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
        return new ResponseEntity<Genre>(genre, status);
	}

	/**
	 * 
	 * @param genre
	 * @return
	 */
	@DeleteMapping(path="/{genreId}", 
					produces = {
						MediaType.APPLICATION_XML_VALUE,
						MediaType.APPLICATION_JSON_VALUE
					})
	public ResponseEntity<Genre> deleteGenre(@PathVariable int genreId) {
		Genre genre = null;
		HttpStatus status = HttpStatus.OK;
        try {
			genre= genreService.readGenre(genreId);
			if(genre == null)
				status = HttpStatus.NOT_FOUND;
			else
				genreService.deleteGenre(genre);
		} catch (ClassNotFoundException | SQLException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
        return new ResponseEntity<Genre>(genre, status);
	}
}
