/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import beans.Book;
import db.DB_Access;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author martin
 */
public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, FileNotFoundException {
        DB_Access acc = new DB_Access();
        List<Book> books = acc.getBooksForCriteria("", "", "", "", "");
        System.out.println("Size is: " + books.size());
    }
}
