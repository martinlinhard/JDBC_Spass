/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author martin
 */
public class EmployeeModel extends AbstractTableModel {

    private final String[] columns = {"Name", "Gender", "Birthdate", "Hiredate"};

    public EmployeeModel(List<Object[]> employees) {
        this.employees = employees;
        this.fireTableDataChanged();
    }

    // This represents each employee as an array of objects (obtained by calling
    // toObjectArray on the Employee) (on which toString() is being called on 
    // by the AbstractTableModel)
    private List<Object[]> employees;

    @Override
    public int getRowCount() {
        return this.employees.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        // The first index marks the row, the second the column
        // respectively
        return this.employees.get(i)[i1];
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    public void setEmployees(List<Object[]> employees) {
        this.employees = employees;
        this.fireTableDataChanged();
    }
}
