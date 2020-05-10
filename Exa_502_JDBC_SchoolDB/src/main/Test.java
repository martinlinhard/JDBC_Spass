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
        //load students
        List<Student> students = IOHandler.load();

        //load their grades
        Set<Grade> grades = Student.getGradesForStudents(students);

        //insert their grades
        for (Grade g : grades) {
            exec.insertGrade(g);
        }

        //generate mappings
        Map<String, Integer> mappings = Grade.toClassMappings(grades);

        //insert students
        for (Student s : students) {
            exec.insertStudent(s, mappings);
        }

        //split them into their respected classes
        HashMap<String, List<Student>> split = Student.splitIntoClasses(students);

        for (List<Student> curr : split.values()) {
            // and update their catnos
            exec.updateStudents(curr);
        }
    }
}
