/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import beans.Employee;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author martin
 */
public class IOHandler {
    private static final String PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator + "res" + File.separator + "input.csv";
    
    public static List<Employee> readEmployees() throws FileNotFoundException {
        return new BufferedReader(new FileReader(PATH))
                .lines()
                .skip(1)
                .map(Employee::fromLine)
                .collect(Collectors.toList());
    }
}
