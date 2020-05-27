/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author martin
 */
public class Book {

    private int bookID;
    private String title;
    private String isbnNr;
    private List<Author> authors;
    private String publisher;
    private int pages;
    private float rating;
    private List<Genre> genre;

    public Book(int bookID, String title, String isbnNr, List<Author> authors, String publisher, int pages, float rating, List<Genre> genre) {
        this.bookID = bookID;
        this.title = title;
        this.isbnNr = isbnNr;
        this.authors = authors;
        this.publisher = publisher;
        this.pages = pages;
        this.rating = rating;
        this.genre = genre;
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
        });
    }

    @Override
    public String toString() {
        return this.title;
    }

    @Override
    public int hashCode() {

        ArrayList<String> gfg = new ArrayList<String>() {
            {
                add("Geeks");
                add("for");
                add("Geeks");
            }
        };
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
}
