/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    
    private int empno;
    
    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    
    public Employee(String firstname, String lastname, String gender, LocalDate birthDate, LocalDate hireDate, int empno) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.hireDate = hireDate;
        this.empno = empno;
    }
    
    public static Employee fromSQLSet(ResultSet rs) throws SQLException {
        return new Employee(
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("gender"),
                rs.getDate("birth_date").toLocalDate(),
                rs.getDate("hire_date").toLocalDate(),
                rs.getInt("emp_no")
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

    public int getEmpno() {
        return empno;
    }
    
    
    
    private String formatName() {
        return String.format("%s, %s", this.lastname, this.firstname);
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }
    
    
    
    public Object[] toObjectArray() {
        return new Object[]{this.formatName(), this.gender, dtf.format(this.birthDate), dtf.format(this.hireDate)};
    }
}
