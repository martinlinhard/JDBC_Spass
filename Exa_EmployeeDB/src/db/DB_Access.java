/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import beans.Department;
import beans.Employee;
import beans.GenderFilter;
import beans.Manager;
import beans.SalaryHistory;
import beans.SortType;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author martin
 */
public class DB_Access {

    private DB_CachedConnection cconn;

    public DB_Access() throws SQLException, ClassNotFoundException, FileNotFoundException {
        DB_Database db = new DB_Database();
        db.connect();
        this.cconn = new DB_CachedConnection(db.getRawConnection());
        this.cconn.setup();
    }

    public List<Department> loadDepartments() throws SQLException {
        ResultSet rs = this.cconn.getGenericStatement().executeQuery(DB_CachedConnection.dpStatementString);
        List<Department> departments = new LinkedList<>();
        while (rs.next()) {
            departments.add(Department.fromSQLSet(rs));
        }
        return departments;
    }

    public List<Employee> getEmployeesForCriteria(String deptno, LocalDate birthdate, GenderFilter genderFilter, SortType type) throws SQLException {
        List<Employee> employees = new LinkedList<>();

        // In case no birthdate was given, we choose the current date
        // which acts as though there was no filter applied in the first place
        if (birthdate == null) {
            birthdate = LocalDate.now();
        }

        String statement = EmployeeStatementFactory.getStatementForInput(deptno, birthdate, genderFilter, type);

        ResultSet rs = this.cconn.getGenericStatement().executeQuery(statement);

        while (rs.next()) {
            employees.add(Employee.fromSQLSet(rs));
        }

        return employees;
    }

    public List<Manager> getManagerForCriteria(String deptno) throws SQLException {
        List<Manager> managers = new LinkedList<>();
        PreparedStatement p = this.cconn.getManagerStatement();
        p.setString(1, deptno);
        ResultSet rs = p.executeQuery();

        while (rs.next()) {
            managers.add(Manager.fromSQLSet(rs));
        }
        return managers;
    }

    public void alterFirstLastname(Employee e) throws SQLException {
        PreparedStatement p = this.cconn.getUpdateFirstLastNameStatement();
        p.setString(1, e.getFirstname());
        p.setString(2, e.getLastname());
        p.setInt(3, e.getEmpno());
        p.executeUpdate();
    }

    public void alterHiredate(Employee e) throws SQLException {
        PreparedStatement p = this.cconn.getUpdateHiredateStatement();
        p.setDate(1, Date.valueOf(e.getHireDate()));
        p.setInt(2, e.getEmpno());
        p.executeUpdate();
    }

    public List<SalaryHistory> retrieveSalaryForEmployee(int empno) throws SQLException {
        List<SalaryHistory> salaries = new LinkedList<>();
        PreparedStatement p = this.cconn.getRetrieveSalaryStatement();
        p.setInt(1, empno);
        ResultSet rs = p.executeQuery();
        while (rs.next()) {
            salaries.add(SalaryHistory.fromSQLSet(rs));
        }
        return salaries;
    }
}
