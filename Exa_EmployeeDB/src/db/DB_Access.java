/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;


import beans.Department;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author martin
 */
public class DB_Access {

    private DB_CachedConnection cconn;

    public DB_Access() throws SQLException, ClassNotFoundException, FileNotFoundException {
        DB_Database db = new DB_Database();
        db.connect();
        this.cconn = new DB_CachedConnection(db.getRawConnection());
        this.cconn.setup();
    }
    
    public List<Department> loadDepartments() throws SQLException {
        ResultSet rs = this.cconn.getGenericStatement().executeQuery(DB_CachedConnection.dpStatementString);
        List<Department> departments = new LinkedList<>();
        while(rs.next()) {
            departments.add(Department.fromSQLSet(rs));
        }
        return departments;
    }
}
