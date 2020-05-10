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
        PreparedStatement insert = this.dbConn.prepareStatement("INSERT INTO public.student(\n"
                + "	classid, catno, firstname, surname, gender, dateofbirth)\n"
                + "	VALUES (?, ?, ?, ?, ?, ?)\n"
                + "     RETURNING studentid;");

        PreparedStatement getS = this.dbConn.prepareStatement("SELECT studentid AS \"studentid\", g.classname AS \"classname\", catno AS \"catno\", firstname AS \"firstname\", surname AS \"surname\", gender AS \"gender\", birthdate AS \"dateofbirth\"\n"
                + "FROM student\n"
                + "INNER JOIN grade g ON s.classid = g.classid;");

        PreparedStatement getG = this.dbConn.prepareStatement("SELECT * FROM grade;");

        PreparedStatement insertG = this.dbConn.prepareStatement("INSERT INTO public.grade(\n"
                + "	classname)\n"
                + "	VALUES (?)"
                + "     RETURNING classid;");
        
        PreparedStatement getSForClass = this.dbConn.prepareStatement("SELECT studentid AS \"studentid\", g.classname AS \"classname\", catno AS \"catno\", firstname AS \"firstname\", surname AS \"surname\", gender AS \"gender\", birthdate AS \"dateofbirth\"\n"
                + "FROM student\n"
                + "INNER JOIN grade g ON s.classid = g.classid\n"
                + "WHERE g.classname = ?;");

        //UPDATE films SET kind = 'Dramatic' WHERE kind = 'Drama';
        PreparedStatement updateID = this.dbConn.prepareStatement("UPDATE student SET catno = ? WHERE studentid = ?;");

        this.statements.put(StatementType.INSERT_STUDENT, insert);
        this.statements.put(StatementType.GET_STUDENTS, getS);
        this.statements.put(StatementType.GET_GRADES, getG);
        this.statements.put(StatementType.INSERT_GRADE, insertG);
        this.statements.put(StatementType.UPDATE_CATNO, updateID);
        this.statements.put(StatementType.GET_STUDENTS_FOR_CLASS, getSForClass);
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

enum StatementType {
    INSERT_STUDENT,
    INSERT_GRADE,
    DELETE_STUDENT,
    UPDATE_CATNO,
    GET_STUDENTS,
    GET_STUDENTS_FOR_CLASS,
    GET_GRADES,
}
