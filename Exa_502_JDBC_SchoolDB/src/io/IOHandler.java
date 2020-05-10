/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import beans.Student;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author martin
 */
public class IOHandler {

    private static final Path PATH = Paths.get(System.getProperty("user.dir"), "src", "res", "Studentlist_3xHIF.csv");

    public static List<Student> load() throws FileNotFoundException {
        return new BufferedReader(new FileReader(PATH.toFile()))
                .lines()
                .skip(1)
                .map(Student::fromLine)
                .collect(Collectors.toList());
    }
}