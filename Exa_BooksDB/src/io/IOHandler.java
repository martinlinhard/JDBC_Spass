/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author martin
 */
public class IOHandler {

    /**
     *
     * @return A String which contains the content of the file (i.e. the SQL
     * statement)
     * @throws FileNotFoundException
     */
    public static String bookStatementS;
    public static String bookTemplate;

    static {
        try {
            bookStatementS = IOHandler.getString(Paths.get(System.getProperty("user.dir"), "src", "res", "book_query.sql"));
            bookTemplate = IOHandler.getString(Paths.get(System.getProperty("user.dir"), "src", "res", "book_template.html"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IOHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static String getString(Path p) throws FileNotFoundException {
        return new BufferedReader(new FileReader(p.toFile())).lines().collect(Collectors.joining("\n"));
    }
}
