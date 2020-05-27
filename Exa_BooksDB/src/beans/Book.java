/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import io.IOHandler;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author martin
 */
public class Book {

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private int bookID;
    private String title;
    private String isbnNr;
    private List<Author> authors;
    private String publisher;
    private int pages;
    private float rating;
    private List<Genre> genre;
    private LocalDate published;

    public Book(int bookID, String title, String isbnNr, List<Author> authors, String publisher, int pages, float rating, List<Genre> genre, LocalDate published) {
        this.bookID = bookID;
        this.title = title;
        this.isbnNr = isbnNr;
        this.authors = authors;
        this.publisher = publisher;
        this.pages = pages;
        this.rating = rating;
        this.genre = genre;
        this.published = published;
    }

    public int getBookID() {
        return bookID;
    }

    public Book(ResultSet set) throws SQLException {
        this(set.getInt("ID"), set.getString("title"), set.getString("ISBN"),
                new ArrayList<Author>() {
            {
                add(new Author(set.getString("firstname"), set.getString("middlename"), set.getString("lastname")));
            }
        },
                set.getString("publisher_name"),
                set.getInt("pages"),
                set.getFloat("rating"),
                new ArrayList<Genre>() {
            {
                add(new Genre(set.getString("genre")));
            }
        },
                set.getDate("published_date").toLocalDate());
    }

    @Override
    public String toString() {
        return this.title;
    }

    public String renderToHTMLString() {
        return IOHandler.bookTemplate
                .replace("{ISBN}", this.isbnNr)
                .replace("{PAGES}", this.pages + "")
                .replace("{GENRE}", this.genre.stream().map(Genre::getName).collect(Collectors.joining(", ")))
                .replace("{RATING}", String.format("%.2f", this.rating))
                .replace("{PUBLISHED_ON}", DTF.format(this.published))
                .replace("{PUBLISHER}", this.publisher)
                .replace("{TITLE}", this.title)
                .replace("{AUTHOR}", this.authors.stream().map(a -> a.getLastname() + ", " + a.getFirstname()).collect(Collectors.joining("<br>")));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.bookID;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Book other = (Book) obj;
        if (this.bookID != other.bookID) {
            return false;
        }
        return true;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbnNr() {
        return isbnNr;
    }

    public void setIsbnNr(String isbnNr) {
        this.isbnNr = isbnNr;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<Genre> getGenre() {
        return genre;
    }

    public void setGenre(List<Genre> genre) {
        this.genre = genre;
    }

}
