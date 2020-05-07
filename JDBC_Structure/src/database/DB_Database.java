/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author martin
 */
public class DB_Database {
    
    private String dbUrl;
    private String dbDriver;
    private String dbUsername;
    private String dbPassword;
    
    private Connection conn;
    private DB_CachedConnection cconn;
    
    public DB_Database() throws ClassNotFoundException, SQLException {
        this.loadProps();
        Class.forName(dbDriver);
        this.connect();
    }
    
    private void loadProps() {
        this.dbUrl = DB_Properties.getProp("url");
        this.dbDriver = DB_Properties.getProp("driver");
        this.dbUsername = DB_Properties.getProp("username");
        this.dbPassword = DB_Properties.getProp("password");
    }
    
    public void connect() throws SQLException {
        if (this.conn != null) {
            this.conn.close();
        }
        this.conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        this.cconn = new DB_CachedConnection(conn);
    }
    
    public void disConnect() throws SQLException {
        if (this.conn != null) {
            if (!this.conn.isClosed()) {
                this.conn.close();
            }
        }
    }
    
    public Statement getStatement() throws SQLException {
        if (this.conn != null && this.cconn != null) {
            return this.cconn.getStatement();
        }
        throw new RuntimeException("askldfjklöadsj f");
    }
    
    public void returnStatement(Statement s) {
        if (this.conn != null && this.cconn != null) {
            this.cconn.returnStatement(s);
        }
        throw new RuntimeException("askldfjklöadsj f");
    }
}
