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
public class SalaryHistory {

    private int salary;
    private LocalDate fromDate;
    private LocalDate toDate;

    public SalaryHistory(int salary, LocalDate fromDate, LocalDate toDate) {
        this.salary = salary;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public int getSalary() {
        return salary;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public static SalaryHistory fromSQLSet(ResultSet rs) throws SQLException {
        return new SalaryHistory(
                rs.getInt("salary"),
                rs.getDate("from_date").toLocalDate(),
                rs.getDate("to_date").toLocalDate()
        );
    }

    @Override
    public String toString() {
        return String.format("<span style=\"color: red;\">%d €</span>&nbsp;&nbsp;from %s to %s", this.salary, Employee.dtf.format(this.fromDate), Employee.dtf.format(this.toDate));
    }
}
