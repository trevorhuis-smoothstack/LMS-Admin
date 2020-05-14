package com.ss.training.lms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="tbl_book_genres")
@IdClass(BookGenreId.class)
public class BookGenre implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 6979716688588974775L;

    @Id
    @Column(name="genre_id")
    private Integer genreId;

    @Id
    @Column(name="bookId")
    private Integer bookId;

    public BookGenre(Integer genreId, int bookId) {
        this.genreId = genreId;
        this.bookId = bookId;
	}

	public BookGenre() {
	}

	public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bookId == null) ? 0 : bookId.hashCode());
        result = prime * result + ((genreId == null) ? 0 : genreId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BookGenre other = (BookGenre) obj;
        if (bookId == null) {
            if (other.bookId != null)
                return false;
        } else if (!bookId.equals(other.bookId))
            return false;
        if (genreId == null) {
            if (other.genreId != null)
                return false;
        } else if (!genreId.equals(other.genreId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "BookGenre [bookId=" + bookId + ", genreId=" + genreId + "]";
    }
}