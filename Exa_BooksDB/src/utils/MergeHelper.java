/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import beans.Author;
import beans.Book;
import beans.Genre;
import beans.MergeResult;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author martin
 */
public class MergeHelper {

    public static MergeResult mergeBooks(List<Book> books) {
        // Books are now grouped by their ID --> now, merge their authors & genres
        Collection<List<Book>> merged = books.stream().collect(Collectors.groupingBy(Book::getBookID)).values();

        List<Book> newBooks = new ArrayList<>();
        Set<Genre> allGenres = new HashSet<>();
        Set<String> allPublishers = new HashSet<>();

        merged.forEach(bl -> {
            Set<Author> authors = new HashSet<>();
            Set<Genre> genres = new HashSet<>();
            bl.forEach(book -> {
                authors.addAll(book.getAuthors());
                genres.addAll(book.getGenre());
                allGenres.addAll(book.getGenre());
                allPublishers.add(book.getPublisher());
            });
            Book b = bl.get(0); // get first book, which one doesn't really matter
            b.setAuthors(new ArrayList<>(authors));
            b.setGenre(new ArrayList<>(genres));
            newBooks.add(b);
        });

        return new MergeResult(newBooks, allGenres, allPublishers);
    }
}
