/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import beans.Book;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;

/**
 *
 * @author martin
 */
public class BookListModel extends AbstractListModel{

    private List<Book> allBooks;

    public BookListModel() {
        this.allBooks = new ArrayList<>();
    }

    public BookListModel(List<Book> allBooks) {
        this.allBooks = allBooks;
    }

    public void setAllBooks(List<Book> allBooks) {
        this.allBooks = allBooks;
        this.fireContentsChanged(this, 0, this.allBooks.size());
    }
    
    @Override
    public int getSize() {
        return this.allBooks.size();
    }

    @Override
    public Book getElementAt(int i) {
        return this.allBooks.get(i);
    }
    
}
