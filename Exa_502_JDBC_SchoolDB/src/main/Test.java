/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import beans.Grade;
import beans.Student;
import database.DB_StatementExecutionHandler;
import io.IOHandler;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author martin
 */
public class Test {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, FileNotFoundException {
        DB_StatementExecutionHandler exec = new DB_StatementExecutionHandler();
        exec.connect();
        List<Student> students = IOHandler.load();
        Set<Grade> grades = Student.getGradesForStudents(students);

        for (Grade g : grades) {
            int val = exec.insertGrade(g);
            g.setClassid(val);
        }

        Map<String, Integer> mappings = Grade.toClassMappings(grades);
        
        Student s = new Student(0, "1DHIF", -5, "test", "test", "m", LocalDate.now());
        Student s2 = new Student(0, "3DHIF", -5, "Alex", "Kirschner", "m", LocalDate.now());
        
        exec.insertStudent(s, mappings);
        exec.insertStudent(s2, mappings);
        
        students = new ArrayList<>();
        students.add(s);
        students.add(s2);
        exec.updateStudents(students);
    }
}
