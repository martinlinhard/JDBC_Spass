/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author martin
 */
public class DB_CachedConnection {

    private PreparedStatement managerStatement;
    private PreparedStatement updateFirstLastNameStatement;
    private PreparedStatement updateHiredateStatement;
    private PreparedStatement retrieveSalaryStatement;
    private Statement genericStatement;

    private Connection dbConn;

    public static String dpStatementString = "select * from departments";

    public DB_CachedConnection(Connection dbConn) throws SQLException {
        this.dbConn = dbConn;
    }

    public void setup() throws SQLException, FileNotFoundException {
        this.managerStatement = this.dbConn.prepareStatement("SELECT dm.from_date, dm.to_date, e.first_name, e.last_name\n"
                + "FROM departments d\n"
                + "INNER JOIN dept_manager dm ON d.dept_no = dm.dept_no\n"
                + "INNER JOIN employees e ON e.emp_no = dm.emp_no\n"
                + "WHERE d.dept_no = ?;");
        this.genericStatement = this.dbConn.createStatement();
        this.updateFirstLastNameStatement = this.dbConn.prepareStatement("UPDATE employees SET first_name = ?, last_name = ? WHERE emp_no = ?;");
        this.updateHiredateStatement = this.dbConn.prepareStatement("UPDATE employees SET hire_date = ? WHERE emp_no = ?;");
        this.retrieveSalaryStatement = this.dbConn.prepareStatement("SELECT salary, from_date, to_date FROM salaries WHERE emp_no = ?;");
    }

    public void closeAllStatements() throws SQLException {
        this.managerStatement.close();
        this.updateFirstLastNameStatement.close();
        this.updateHiredateStatement.close();
        this.genericStatement.close();
    }

    public PreparedStatement getManagerStatement() {
        return managerStatement;
    }

    // This is used for retrieving all departments / all employees
    public Statement getGenericStatement() {
        return this.genericStatement;
    }

    public PreparedStatement getUpdateFirstLastNameStatement() {
        return updateFirstLastNameStatement;
    }

    public PreparedStatement getUpdateHiredateStatement() {
        return updateHiredateStatement;
    }

    public PreparedStatement getRetrieveSalaryStatement() {
        return retrieveSalaryStatement;
    }
}
