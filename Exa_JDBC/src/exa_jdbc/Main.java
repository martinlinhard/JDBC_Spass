/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exa_jdbc;

import java.sql.SQLException;

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
		tester.connect("postgres");
		tester.disconnect();
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}
  }
}
