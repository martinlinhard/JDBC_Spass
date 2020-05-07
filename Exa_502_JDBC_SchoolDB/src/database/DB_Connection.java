/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author martin
 */
public class DB_Connection {
    private Connection connection;
    
    private String username;
    private String url;
    private String password;
    

    public DB_Connection() throws ClassNotFoundException {
        Class.forName(DB_Properties.getProp("driver"));
        
        this.username = DB_Properties.getProp("username");
        this.url = DB_Properties.getProp("url");
        this.password = DB_Properties.getProp("password");
    }
    
    public void connect() throws SQLException {
        this.connection = DriverManager.getConnection(url, username, password);
    }
    
    public void disconnect() throws SQLException {
        if (this.connection != null) {
            if (!this.connection.isClosed()) {
                this.connection.close();
            }
        }
    }

    public Connection getRawConnection() {
        return connection;
    }
}
