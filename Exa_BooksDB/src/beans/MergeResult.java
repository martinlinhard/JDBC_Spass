/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.List;
import java.util.Set;

/**
 *
 * @author martin
 */
public class MergeResult {

    private List<Book> allBooks;
    private Set<Genre> allGenres;
    private Set<String> allPublishers;

    public MergeResult(List<Book> allBooks, Set<Genre> allGenres, Set<String> allPublishers) {
        this.allBooks = allBooks;
        this.allGenres = allGenres;
        this.allPublishers = allPublishers;
    }

    public List<Book> getAllBooks() {
        return allBooks;
    }

    public Set<Genre> getAllGenres() {
        return allGenres;
    }

    public Set<String> getAllPublishers() {
        return allPublishers;
    }
    
}
