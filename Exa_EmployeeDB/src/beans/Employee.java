/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author martin
 */
public class Employee {

    private String firstname;
    private String lastname;

    private String gender;

    private LocalDate birthDate;
    private LocalDate hireDate;

    public Employee(String firstname, String lastname, String gender, LocalDate birthDate, LocalDate hireDate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.hireDate = hireDate;
    }

    public static Employee fromSQLSet(ResultSet rs) throws SQLException {
        return new Employee(
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("gender"),
                rs.getDate("birth_date").toLocalDate(),
                rs.getDate("hire_date").toLocalDate()
        );
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }
}
