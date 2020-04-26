/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exa_jdbc;

import java.sql.SQLException;
import java.time.LocalDate;

import exa_jdbc.beans.Student;

/**
 *
 * @author martin
 */
public class Main {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    try {
		DBTester tester = new DBTester();
		tester.connect("studentdb");
		//tester.createTable();
		tester.insertStudent(new Student(0, 1, "Leon", "Anghel", LocalDate.now()));
		tester.insertStudent(new Student(0, 2, "Nico", "Baumann", LocalDate.now()));
		tester.insertStudent(new Student(0, 3, "Adrian", "Berner", LocalDate.now()));
		tester.getStudents();
		tester.disconnect();
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}
  }
}
