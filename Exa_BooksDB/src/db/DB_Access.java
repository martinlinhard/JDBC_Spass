/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import beans.Book;
import beans.Genre;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author martin
 */
public class DB_Access {

    private DB_CachedConnection cconn;

    public DB_Access() throws SQLException, ClassNotFoundException, FileNotFoundException {
        DB_Database db = new DB_Database();
        db.connect();
        this.cconn = new DB_CachedConnection(db.getRawConnection());
        this.cconn.setup();
    }

    /**
     *
     * @param values: firstname, lastname, title, genre, publisher!
     * @return A list of all books matching the search criteria
     */
    public List<Book> getBooksForCriteria(String... values) throws SQLException {
        PreparedStatement p = this.cconn.getBookStatement();

        // Loop through all arguments; they must be passed in in the right order!
        for (int i = 0; i < values.length; i++) {
            p.setString(i + 1, "%" + values[i] + "%");
        }

        List<Book> books = new ArrayList<>();

        ResultSet set = p.executeQuery();

        while (set.next()) {
            books.add(new Book(set));
        }

        return books;
    }
}
