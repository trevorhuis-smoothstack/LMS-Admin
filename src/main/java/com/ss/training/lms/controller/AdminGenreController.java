package com.ss.training.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.training.lms.entity.Genre;
import com.ss.training.lms.service.admin.AdminGenreService;

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
	@GetMapping(path="/{genreId}")
	public ResponseEntity<Genre>readGenre(@PathVariable int genreId) {
		Genre genre = null;
		HttpStatus status = HttpStatus.OK;

		genre= genreService.readGenre(genreId);
		if (genre == null) {
			status = HttpStatus.NOT_FOUND;
		}

		return new ResponseEntity<Genre>(genre, status);
	}

	/**
	 * 
	 * @return
	 */
	@GetMapping()
	public ResponseEntity<Genre[]> readGenres() {
		List<Genre> genreList = null;
		Genre[] genreArray = null;
		HttpStatus status = HttpStatus.OK;
		genreList = genreService.readAllGenres();
		if (genreList == null) // no genres exist in the database
			status = HttpStatus.NO_CONTENT;
		else
			genreArray = genreList.toArray(new Genre[genreList.size()]);
		return new ResponseEntity<Genre[]>(genreArray, status);
	}

	/**
	 * 
	 * @param genre
	 * @return
	 */
	@PostMapping()
	public ResponseEntity<Genre> createGenre(@RequestBody Genre genre) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		if(genre == null || genre.getGenreId() == null || (genre.getGenreName() != null && genre.getGenreName().length() > 45))
				return new ResponseEntity<Genre>(genre, HttpStatus.BAD_REQUEST);

		genreService.saveGenre(genre);
		status = HttpStatus.CREATED;

        return new ResponseEntity<Genre>(genre, status);
		
	}

	/**
	 * 
	 * @param genre
	 * @return
	 */
	@PutMapping()
	public ResponseEntity<Genre> updateGenre(@RequestBody Genre genre) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		if(genre == null || genre.getGenreId() == null || (genre.getGenreName() != null && genre.getGenreName().length() > 45))
			return new ResponseEntity<Genre>(genre, status);

		if(genreService.readGenre(genre.getGenreId()) == null)
			return new ResponseEntity<Genre>(genre, HttpStatus.NOT_FOUND);
		genreService.saveGenre(genre);
		status = HttpStatus.CREATED;


        return new ResponseEntity<Genre>(genre, status);
	}

	/**
	 * 
	 * @param genre
	 * @return
	 */
	@DeleteMapping(path="/{genreId}")
	public ResponseEntity<Genre> deleteGenre(@PathVariable int genreId) {
		Genre genre = null;
		HttpStatus status = HttpStatus.OK;

		genre= genreService.readGenre(genreId);
		if(genre == null)
			status = HttpStatus.NOT_FOUND;
		else
			genreService.deleteGenre(genre);

        return new ResponseEntity<Genre>(genre, status);
	}
}
