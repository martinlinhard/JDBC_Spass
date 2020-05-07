/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author martin
 */
public class DB_CachedConnection {
    private Queue<Statement> statements = new LinkedList<>();
    
    private Connection connection;

    public DB_CachedConnection(Connection connection) {
        this.connection = connection;
    }
    
    public Statement getStatement() throws SQLException {
        if(connection == null) {
            throw new RuntimeException("you shall not get a connection");
        }
        
        if (!statements.isEmpty()) {
            return statements.poll();
        }
        return this.connection.createStatement();
    }
    
    public void returnStatement(Statement s) {
        if (s == null)
            throw new RuntimeException("nope");
        this.statements.offer(s); // offer == add
    }
}