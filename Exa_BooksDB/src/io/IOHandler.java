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
    public static String getStatement() throws FileNotFoundException {
        Path p = Paths.get(System.getProperty("user.dir"), "src", "res", "book_query.sql");
        return new BufferedReader(new FileReader(p.toFile())).lines().collect(Collectors.joining("\n"));
    }
}
