/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author martin
 */
public class Employee implements Comparable<Employee> {

    public static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static int employeeCounter = 0;
    private int persNR;
    private String name;
    private String vorname;
    private LocalDate gebDatum;
    private double gehalt;
    private int abtNR;
    private Gender gender;

    public Employee(int persNR, String name, String vorname, LocalDate gebDatum, double gehalt, int abtNR, Gender gender) {
        this.persNR = persNR;
        this.name = name;
        this.vorname = vorname;
        this.gebDatum = gebDatum;
        this.gehalt = gehalt;
        this.abtNR = abtNR;
        this.gender = gender;
    }

    public Employee(String name, String vorname, LocalDate gebDatum, double gehalt, int abtNR, Gender gender) {
        //CAREFUL WHEN USING THIS CONSTRUCTOR: setPersNR NEEDS TO BE CALLED AFTERWARDS!
        this.persNR = 0;
        this.name = name;
        this.vorname = vorname;
        this.gebDatum = gebDatum;
        this.gehalt = gehalt;
        this.abtNR = abtNR;
        this.gender = gender;
    }

    public int getPersNR() {
        return persNR;
    }

    public String getName() {
        return name;
    }

    public String getVorname() {
        return vorname;
    }

    public LocalDate getGebDatum() {
        return gebDatum;
    }

    public double getGehalt() {
        return gehalt;
    }

    public int getAbtNR() {
        return abtNR;
    }

    public Gender getGender() {
        return gender;
    }

    public void setPersNR(int persNR) {
        this.persNR = persNR;
    }

    public static List<Employee> getEmployeesFromSet(ResultSet s) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        while (s.next()) {
            employees.add(new Employee(s.getInt("pers_nr"), s.getString("name"), s.getString("vorname"), s.getDate("geb_datum").toLocalDate(), s.getDouble("gehalt"), s.getInt("abt_nr"), s.getString("geschlecht").equals("M") ? Gender.Male : Gender.Female));
        }
        return employees;
    }

    public static TreeSet<Employee> getEmployeesSetFromSet(ResultSet s) throws SQLException {
        TreeSet<Employee> employees = new TreeSet<>();
        while (s.next()) {
            employees.add(new Employee(s.getInt("pers_nr"), s.getString("name"), s.getString("vorname"), s.getDate("geb_datum").toLocalDate(), s.getDouble("gehalt"), s.getInt("abt_nr"), s.getString("geschlecht").equals("M") ? Gender.Male : Gender.Female));
        }
        return employees;
    }

    public void insertEmployee(PreparedStatement st) throws SQLException {
        st.setInt(1, this.persNR);
        st.setString(2, this.name);
        st.setString(3, this.vorname);
        st.setDate(4, java.sql.Date.valueOf(this.gebDatum));
        st.setDouble(5, gehalt);
        st.setInt(6, abtNR);
        st.setString(7, this.gender == Gender.Male ? "M" : "F");

        st.executeUpdate();
    }

    public static Employee fromLine(String line) {
        String[] tokens = line.split(",");
        return new Employee(tokens[0], tokens[1], LocalDate.parse(tokens[2], DTF), Double.parseDouble(tokens[3]), Integer.parseInt(tokens[4]), tokens[5].equals("M") ? Gender.Male : Gender.Female);
    }

    @Override
    public String toString() {
        String stuff = String.format("%s %s (%s, %s)", name, vorname, gender == Gender.Male ? "M" : "F", DTF.format(gebDatum));
        return String.format("%d | %-50s | %.2f (A: %d)", persNR, stuff, gehalt, abtNR);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.name);
        hash = 71 * hash + Objects.hashCode(this.vorname);
        hash = 71 * hash + Objects.hashCode(this.gebDatum);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Employee other = (Employee) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.vorname, other.vorname)) {
            return false;
        }
        if (!Objects.equals(this.gebDatum, other.gebDatum)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Employee t) {
        //to sort them by persNR
        return this.persNR - t.persNR;
    }
}
