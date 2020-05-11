package com.ss.training.lms.service.admin;

import com.ss.training.lms.jdbc.ConnectionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
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
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void createGenre(Genre genre) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        boolean success = false;
        try {
            conn = connUtil.getConnection();
            genre.setGenreId( genreDAO.createGenre(genre, conn));
            success = true;
        } finally {
            if(success)
                conn.commit();
            else
                conn.rollback();
            if(conn != null)
                conn.close();
        }
    }

    /**
     * 
     * @param genre
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void deleteGenre(Genre genre) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        boolean success = false;

        try {
            conn = connUtil.getConnection();
            bookGenreDAO.deleteGenresReferenceByGenre(genre.getGenreId(), conn);
            genreDAO.deleteGenre(genre, conn);
            success= true;
        } finally {
            if(success)
                conn.commit();
            else
                conn.rollback();
            if(conn != null)
                conn.close();
        }
    }

    /**
     * 
     * @param genre
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void updateGenre(Genre genre) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        boolean success = false;
        try {
            conn = connUtil.getConnection();
            genreDAO.updateGenre(genre, conn);
            success= true;
        } finally {
            if(success)
                conn.commit();
            else
                conn.rollback();
            if(conn != null)
                conn.close();
        }
    }

    /**
     * 
     * @param genreId
     * @return
     * @throws SQLException
     */
    public Genre readGenre(Integer genreId) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            List<Genre> genres = genreDAO.readGenre(genreId, conn);
            if(genres.size() == 0) {
                return null;
            }
            return genres.get(0);
        } finally {
            if(conn != null)
                conn.close();
        }
    }

    /**
     * 
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public List<Genre> readAllGenres() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            List<Genre> genres = genreDAO.readAllGenres(conn);
            if(genres.size() == 0) {
                return null;
            }
            return genres;
        } finally {
            if(conn != null)
                conn.close();
        }
    }
}