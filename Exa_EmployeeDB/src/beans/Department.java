/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author martin
 */
public class Department {
    private String deptNo;
    private String name;

    public Department(String deptNo, String name) {
        this.deptNo = deptNo;
        this.name = name;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public static Department fromSQLSet(ResultSet rs) throws SQLException {
        return new Department(rs.getString("dept_no"), rs.getString("dept_name"));
    }

    @Override
    public String toString() {
        return this.name;
    }
}
