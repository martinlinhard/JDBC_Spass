/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import io.IOHandler;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author martin
 */
public class DB_CachedConnection {

    private PreparedStatement bookStatement;

    private Connection dbConn;

    public DB_CachedConnection(Connection dbConn) throws SQLException {
        this.dbConn = dbConn;
    }

    public void setup() throws SQLException, FileNotFoundException {
        this.bookStatement = this.dbConn.prepareStatement(IOHandler.getStatement());
    }

    public void closeAllStatements() throws SQLException {
        this.bookStatement.close();
    }

    public PreparedStatement getBookStatement() {
        return bookStatement;
    }
}
