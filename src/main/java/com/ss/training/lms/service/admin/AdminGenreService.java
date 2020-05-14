package com.ss.training.lms.service.admin;

import com.ss.training.lms.jdbc.ConnectionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import com.ss.training.lms.dao.BookGenreDAO;
import com.ss.training.lms.dao.GenreDAO;
import com.ss.training.lms.entity.Genre;

/**
 * @author Trevor Huis in 't Veld
 */
@Component
public class AdminGenreService {
    @Autowired
    GenreDAO genreDAO;

    @Autowired 
    BookGenreDAO bookGenreDAO;

    @Autowired
    ConnectionUtil connUtil;
    
    /**
     * 
     * @param genre
     */
    public void saveGenre(Genre genre) {
        genreDAO.save(genre);
    }

    /**
     * 
     * @param genre
     */
    public void deleteGenre(Genre genre) {
        genreDAO.delete(genre);
    }


    /**
     * 
     * @param genreId
     * @return
     */
    public Genre readGenre(Integer genreId) {
        return genreDAO.findByGenreId(genreId);
    }

    /**
     * 
     * @return
     */
    public List<Genre> readAllGenres() {
        return genreDAO.findAll();
    }
}