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
		Genre genre = null;
		try {
			genre= genreService.readAGenre(genreId);
		} catch (final SQLException e) {
			e.printStackTrace();
			return new ResponseEntity<Genre>(genre, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Genre>(genre, HttpStatus.OK);
	}

	@RequestMapping(path="/lms/admin/genres", 
					produces = {
						MediaType.APPLICATION_XML_VALUE,
						MediaType.APPLICATION_JSON_VALUE
					})
	public ResponseEntity<List<Genre>> getAllGenres(@PathVariable int genreId) throws SQLException {
		List<Genre> genres = null;
        try {
			genres= genreService.readAllGenres();
		} catch (final SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<List<Genre>>(genres, HttpStatus.BAD_REQUEST);
		}
        return new ResponseEntity<List<Genre>>(genres, HttpStatus.OK);

	}
	
	@RequestMapping(path="/lms/admin/genre",
					method = RequestMethod.POST,
					consumes = {
						MediaType.APPLICATION_JSON_VALUE,
						MediaType.APPLICATION_XML_VALUE
					})
	public void addGenre(@RequestBody Genre genre) throws SQLException {
		genreService.addAGenre(genre);
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
