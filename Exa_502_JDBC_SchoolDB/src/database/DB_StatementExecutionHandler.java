/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import beans.Grade;
import beans.Student;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
        if (this.cache != null)
            this.cache.closeAllStatements();
        this.conn.disconnect();
    }

    public void insertStudent(Student s, Map<String, Integer> classMappings) throws SQLException {
        if (classMappings.containsKey(s.getClassname().toUpperCase())) {
            //Class is already present
            PreparedStatement p = this.cache.getStatementForAction(StatementType.INSERT_STUDENT);

            p.setInt(1, classMappings.get(s.getClassname().toUpperCase()));
            p.setInt(2, 0);
            p.setString(3, s.getFirstname());
            p.setString(4, s.getSurname());
            p.setString(5, s.getGender());
            p.setDate(6, Date.valueOf(s.getDateOfBirth()));

            p.execute();

            // Update the id of the student, since it was assigned by the db
            ResultSet set = p.getResultSet();
            while (set.next()) {
                s.setStudentID(set.getInt("studentid"));
            }
        } else {
            //Class is not present --> Add it to the db & add the returned id to the mappings
            Grade g = new Grade(0, s.getClassname());
            int newID = this.insertGrade(g);
            classMappings.put(g.getClassName(), newID);

            //... and call the function again with the updated mappings :)
            this.insertStudent(s, classMappings);
        }
    }

    public void updateStudents(List<Student> students) throws SQLException {
        //sort them
        Collections.sort(students);

        //reassign catno
        for (int i = 0; i < students.size(); i++) {
            students.get(i).setCatno(i + 1);
        }

        //and update them
        PreparedStatement p = this.cache.getStatementForAction(StatementType.UPDATE_CATNO);
        for (Student s : students) {
            p.setInt(1, s.getCatno());
            p.setInt(2, s.getStudentID());
            p.executeUpdate();
        }
    }

    public List<Student> getAllStudents() throws SQLException {
        PreparedStatement p = this.cache.getStatementForAction(StatementType.GET_STUDENTS);

        ResultSet s = p.executeQuery();
        List<Student> students = Student.parseFromResultSet(s);
        Collections.sort(students);

        return students;
    }

    public List<Student> getAllStudentsForClass(String classname) throws SQLException {
        PreparedStatement p = this.cache.getStatementForAction(StatementType.GET_STUDENTS_FOR_CLASS);
        p.setString(1, classname);

        ResultSet s = p.executeQuery();
        List<Student> students = Student.parseFromResultSet(s);
        Collections.sort(students);

        return students;
    }

    public List<Grade> getAllGrades() throws SQLException {
        PreparedStatement p = this.cache.getStatementForAction(StatementType.GET_GRADES);
        ResultSet s = p.executeQuery();
        List<Grade> grades = Grade.parse(s);
        return grades;
    }

    public int insertGrade(Grade g) throws SQLException {
        PreparedStatement p = this.cache.getStatementForAction(StatementType.INSERT_GRADE);
        p.setString(1, g.getClassName());
        p.execute();
        ResultSet s = p.getResultSet();
        while (s.next()) {
            int val = s.getInt("classid");
            g.setClassid(val);
            return val;
        }
        return -1;
    }
    
    public void clearTables() throws SQLException {
        PreparedStatement p1 = this.cache.getStatementForAction(StatementType.CLEAR_STUDENT);
        PreparedStatement p2 = this.cache.getStatementForAction(StatementType.CLEAR_GRADE);
        
        p1.executeUpdate();
        p2.executeUpdate();
    }
}
