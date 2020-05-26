/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import beans.StatementType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *
 * @author martin
 */
public class DB_CachedConnection {

    private HashMap<StatementType, PreparedStatement> statements;

    private Connection dbConn;

    public DB_CachedConnection(Connection dbConn) throws SQLException {
        this.dbConn = dbConn;
        this.statements = new HashMap<>();
    }

    public void setup() throws SQLException {
        // TODO add all statements to the hashmap
    }

    public PreparedStatement getStatementForAction(StatementType type) {
        return this.statements.get(type);
    }

    public void closeAllStatements() throws SQLException {
        for (PreparedStatement p : this.statements.values()) {
            p.close();
        }
    }

}