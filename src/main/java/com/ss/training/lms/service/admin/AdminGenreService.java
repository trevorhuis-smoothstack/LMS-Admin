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

@Component
public class AdminGenreService {
    @Autowired
    GenreDAO genreDAO;

    @Autowired 
    BookGenreDAO bookGenreDAO;

    @Autowired
    ConnectionUtil connUtil;
    
    public void addGenre(Genre genre) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        boolean success = false;
        try {
            conn = connUtil.getConnection();
            Integer primaryKey = genreDAO.addGenre(genre, conn);
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

    public void deleteGenre(Genre genre) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        boolean success = false;

        try {
            conn = connUtil.getConnection();
            bookGenreDAO.deleteGenresReferenceByGenre(genre.getGenreID(), conn);
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

    public void updateGenre(Genre genre) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        boolean success = false;
        try {
            conn = connUtil.getConnection();
            genreDAO.updateGenre(genre, conn);
            conn.commit();
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

    public Genre readGenre(Integer genreId) throws SQLException {
        Connection conn = null;
        boolean success = false;
        try {
            conn = connUtil.getConnection();
            List<Genre> genres = genreDAO.readGenre(genreId, conn);
            if(genres.size() == 0) {
                return null;
            }
            success= true;
            return genres.get(0);
        } finally {
            if(success)
                conn.commit();
            else
                conn.rollback();
            if(conn != null)
                conn.close();
        }
    }

    public List<Genre> readAllGenres() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        boolean success = false;
        try {
            conn = connUtil.getConnection();
            List<Genre> genres = genreDAO.readAllGenres(conn);
            if(genres.size() == 0) {
                return null;
            }
            success= true;
            return genres;
        } finally {
            if(success)
                conn.commit();
            else
                conn.rollback();
            if(conn != null)
                conn.close();
        }
    }
}