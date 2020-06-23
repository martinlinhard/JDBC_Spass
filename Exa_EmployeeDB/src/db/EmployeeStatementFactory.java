package db;

import beans.GenderFilter;
import beans.SortType;
import java.time.LocalDate;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author martin
 */
public class EmployeeStatementFactory {

    private static String BASE_STATEMENT = "select e.*\n"
            + "from employees e\n"
            + "INNER JOIN dept_emp d ON e.emp_no = d.emp_no\n"
            + "WHERE \n"
            + " birth_date < {BIRTH_DATE}\n"
            + " {GENDER}"
            + " AND d.dept_no = {DEPT_NO}'\n"
            + " ORDER BY {ORDER_TYPE};";

    public static String getStatementForInput(String deptno, LocalDate birthdate, GenderFilter genderFilter, SortType type) {
        // if we want both genders, we simply leave this as-is,
        // since it then avoids another where-clause
        String gender = "";
        switch(genderFilter) {
            case MALE:
                gender = "AND gender = 'M'\n";
                break;
            case FEMALE:
                gender = "AND gender = 'F'\n";
                break;
        }
        return BASE_STATEMENT
                .replace("{BIRTH_DATE}", "'" + birthdate.toString() + "'")
                .replace("{GENDER}", gender)
                .replace("{DEPT_NO}", deptno)
                .replace("{ORDER_TYPE}", type.toString());
    }
}
