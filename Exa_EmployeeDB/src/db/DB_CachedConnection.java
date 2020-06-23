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
    private Statement genericStatement;

    private Connection dbConn;

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
    }

    public void closeAllStatements() throws SQLException {
        this.managerStatement.close();
        this.genericStatement.close();
    }

    public PreparedStatement getManagerStatement() {
        return managerStatement;
    }
    
    // This is used for retrieving all departments / all employees
    public Statement getGenericStatement() {
        return this.genericStatement;
    }
}