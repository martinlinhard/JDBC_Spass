/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author martin
 */
public class DB_StatementExecutionHandler {

    private DB_StatementCache cache;
    private DB_Connection conn;

    public DB_StatementExecutionHandler() throws ClassNotFoundException, SQLException {
        this.conn = new DB_Connection();
    }

    public void connect() throws SQLException {
        this.conn.connect();
        this.cache = new DB_StatementCache(this.conn.getRawConnection());
        this.cache.setup();
    }

    //Closes all statements + the connection
    public void disconnect() throws SQLException {
        this.cache.closeAllStatements();
        this.conn.disconnect();
    }

    public void insertStudent() throws SQLException {
    }
}
