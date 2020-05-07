/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *
 * @author martin
 */
public class DB_StatementCache {

    private PreparedStatement insertStudent;
    private PreparedStatement deleteStudent;

    private HashMap<StatementType, PreparedStatement> statements;

    private Connection dbConn;

    public DB_StatementCache(Connection dbConn) throws SQLException {
        this.dbConn = dbConn;
        this.statements = new HashMap<>();
    }

    public void setup() throws SQLException {
        PreparedStatement insert = this.dbConn.prepareStatement("INSERT INTO users(username, password) VALUES(?, ?);");
        PreparedStatement delete = this.dbConn.prepareStatement("DELETE FROM users WHERE user_id = ?;");

        this.statements.put(StatementType.INSERT_STUDENT, insert);
        this.statements.put(StatementType.DELETE_STUDENT, delete);
    }

    public PreparedStatement getStatementForAction(StatementType type) {
        PreparedStatement p = this.statements.get(type);
        this.statements.replace(type, null);
        return p;
    }

    public void returnStatementForAction(StatementType type, PreparedStatement p) {
        this.statements.replace(type, p);
    }

    public void closeAllStatements() throws SQLException {
        for (PreparedStatement p : this.statements.values()) {
            p.close();
        }
    }
}

enum StatementType {
    INSERT_STUDENT,
    DELETE_STUDENT,
}
