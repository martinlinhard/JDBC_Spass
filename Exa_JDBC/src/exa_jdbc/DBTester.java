package exa_jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import exa_jdbc.beans.Student;

public class DBTester {

    private Connection connection;

    public DBTester() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
    }

    public void connect(String dbName)
            throws ClassNotFoundException, SQLException {
        String url = "jdbc:postgresql://localhost/" + dbName;
        String username = "postgres";
        String password = "postgres";
        this.connection = DriverManager.getConnection(url, username, password);
    }

    public void disconnect() throws SQLException {
        if (this.connection != null) {
            this.connection.close();
        }
    }

    public void createDatabase(String databaseName) throws SQLException {
        String sql = "CREATE DATABASE " + databaseName.toLowerCase();
        Statement s = connection.createStatement();
        s.execute(sql);
        s.close();
    }

    public void createTable() throws SQLException {
        String sql = "CREATE TABLE students ( student_id serial PRIMARY KEY, cat_nr INTEGER NOT NULL, firstname VARCHAR(100) NOT NULL, lastname VARCHAR(100) NOT NULL, birthdate DATE NOT NULL);";
        Statement s = connection.createStatement();
        s.executeUpdate(sql);
        s.close();
    }

    public void insertStudent(Student st) throws SQLException {
        Statement s = this.connection.createStatement();
        Date date = Date.valueOf(st.getBirthdate());
        String sql = String.format("INSERT INTO students(cat_nr, firstname, lastname, birthdate) VALUES (%d, '%s', '%s', '%s');", st.getCatNR(), st.getFirstname(), st.getLastname(), date.toString());
        s.executeUpdate(sql);
        s.close();
    }

    public void getStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        Statement st = this.connection.createStatement();
        ResultSet set = st.executeQuery("SELECT * FROM students;");
        while (set.next()) {
            int id = set.getInt("student_id");
            int catNR = set.getInt("cat_nr");
            String firstname = set.getString("firstname");
            String lastname = set.getString("lastname");
            LocalDate date = set.getDate("birthdate").toLocalDate();
            students.add(new Student(id, catNR, firstname, lastname, date));
        }
        st.close();
        students.stream().forEach(System.out::println);
    }
}
