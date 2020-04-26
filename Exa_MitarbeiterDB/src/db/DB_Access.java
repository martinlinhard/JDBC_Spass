/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import beans.Employee;
import beans.Gender;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import utils.IOHandler;

/**
 *
 * @author martin
 */

public class DB_Access {
    private List<Employee> employees;

    private Connection dbConnection;

    private static final String BASE = "jdbc:postgresql://localhost/";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";

    //STATEMENTS
    private PreparedStatement insertEmployeeST;
    private PreparedStatement getEmployeesForDepartmentST;
    private PreparedStatement getAVGSalForGenderST;
    private PreparedStatement getAllEmployeesST;
    private PreparedStatement deleteEmployeeST;

    public List<Employee> getEmployees() {
        return employees;
    }

    public DB_Access() throws ClassNotFoundException, SQLException, FileNotFoundException {
        Class.forName("org.postgresql.Driver");
        this.setup();
        this.setupStatements();
        this.setupEmployees();
    }

    private void setupEmployees() throws FileNotFoundException, SQLException {
        this.employees = IOHandler.readEmployees();
        for (int i = 0; i < this.employees.size(); i++) {
            this.insertEmployee(this.employees.get(i));
        }
    }

    public void setupStatements() throws SQLException {
        this.insertEmployeeST = this.dbConnection.prepareStatement("INSERT INTO mitarbeiter (pers_nr, name, vorname, geb_datum, gehalt, abt_nr, geschlecht) VALUES (?, ?, ?, ?, ?, ?, ?);");
        this.getEmployeesForDepartmentST = this.dbConnection.prepareStatement("SELECT * FROM mitarbeiter WHERE pers_nr=?;");
        this.getAVGSalForGenderST = this.dbConnection.prepareStatement("SELECT AVG(gehalt) AS \"avg\" FROM mitarbeiter WHERE geschlecht=?;");
        this.getAllEmployeesST = this.dbConnection.prepareStatement("SELECT * FROM mitarbeiter;");
        this.deleteEmployeeST = this.dbConnection.prepareStatement("DELETE FROM mitarbeiter WHERE pers_nr = ?");
    }
    
    private void closeStatements() throws SQLException {
        this.insertEmployeeST.close();
        this.getEmployeesForDepartmentST.close();
        this.getAVGSalForGenderST.close();
        this.getAllEmployeesST.close();
        this.deleteEmployeeST.close();
    }

    private void setup() throws SQLException {
        Connection temp = DriverManager.getConnection(BASE + "postgres", USERNAME, PASSWORD);
        Statement s = temp.createStatement();
        // 1. Drop the database & table in case it already exists
        s.execute("DROP DATABASE IF EXISTS mitarbeiterdb;");
        s.execute("DROP TABLE IF EXISTS public.mitarbeiter;");
        // 2. Create the database
        this.createDB(s);
        // 3. Close no longer needed stuff
        s.close();
        temp.close();
        // 4. Reopen connections to the proper db
        this.connect();
        // 5. Create new statement
        s = this.dbConnection.createStatement();
        // 6. Create the table
        this.createTable(s);
        // 7. Close all statement
        s.close();
    }

    public void connect() throws SQLException {
        this.dbConnection = DriverManager.getConnection(BASE + "mitarbeiterdb", USERNAME, USERNAME);
    }

    public void disconnect() throws SQLException {
        if (this.dbConnection != null) {
            this.dbConnection.close();
            this.closeStatements();
        }
    }

    private void createDB(Statement s) throws SQLException {
        s.execute("CREATE DATABASE mitarbeiterdb;");
    }

    private void createTable(Statement s) throws SQLException {
        s.executeUpdate("CREATE TABLE public.mitarbeiter\n"
                + "(\n"
                + "    abt_nr integer NOT NULL,\n"
                + "    geb_datum date,\n"
                + "    gehalt numeric(7,2),\n"
                + "    name character varying(40) COLLATE pg_catalog.\"default\" NOT NULL,\n"
                + "    pers_nr integer NOT NULL,\n"
                + "    vorname character varying(40) COLLATE pg_catalog.\"default\" NOT NULL,\n"
                + "    geschlecht character(1) COLLATE pg_catalog.\"default\" NOT NULL,\n"
                + "    CONSTRAINT mitarbeiter_pkey PRIMARY KEY (pers_nr)\n"
                + ")\n"
                + "\n"
                + "TABLESPACE pg_default;\n"
                + "\n"
                + "ALTER TABLE public.mitarbeiter\n"
                + "    OWNER to postgres;");
    }

    public void getEmployeesFromDepartment(int department) throws SQLException {
        this.getEmployeesForDepartmentST.setInt(1, department);
        List<Employee> employees = Employee.getEmployeesFromSet(this.getEmployeesForDepartmentST.executeQuery());
        employees.stream().forEach(System.out::println);
    }

    public double getAverageSalery(Gender g) throws SQLException {
        this.getAVGSalForGenderST.setString(1, g  == Gender.Male ? "M" : "F");
        double res = 0;
        ResultSet set = this.getAVGSalForGenderST.executeQuery();
        while (set.next()) {
            res = set.getDouble("avg");
        }
        set.close();
        return res;
    }

    public boolean insertEmployee(Employee e) throws SQLException {
        ResultSet s = this.getAllEmployeesST.executeQuery();
        TreeSet<Employee> emps = Employee.getEmployeesSetFromSet(s);
        
        if (emps.contains(e)) return false;
        
        int persNR = emps.isEmpty() ? 1 : (emps.last().getPersNR() + 1);
        
        e.setPersNR(persNR);
        e.insertEmployee(this.insertEmployeeST); // update number with highest
        return true;
    }

    public int removeEmployee(Employee e) throws SQLException {
        this.deleteEmployeeST.setInt(1, e.getPersNR());
        return this.deleteEmployeeST.executeUpdate();
    }
}