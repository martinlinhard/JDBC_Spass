/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import beans.Employee;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import main.MainGUI;
import static main.MainGUI.main;

/**
 *
 * @author martin
 */
public class EmployeeModel extends AbstractTableModel {

    private final String[] columns = {"Name", "Gender", "Birthdate", "Hiredate"};

    private MainGUI gui;

    // This represents each employee as an array of objects (obtained by calling
    // toObjectArray on the Employee) (on which toString() is being called on 
    // by the AbstractTableModel)
    private List<Object[]> parsedEmployees;
    private List<Employee> employees;

    public EmployeeModel(List<Employee> employees, MainGUI gui) {
        this.employees = employees;
        this.gui = gui;
        this.parseEmployees();
        this.fireTableDataChanged();
    }

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
        return this.parsedEmployees.get(i)[i1];
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
        this.parseEmployees();
        this.fireTableDataChanged();
    }

    private void parseEmployees() {
        this.parsedEmployees = this.employees.stream().map(Employee::toObjectArray).collect(Collectors.toList());
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 0 || column == 3;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        Employee emp = employees.get(row);
        if (col == 0) {
            String[] split = ((String) value).split(",");
            if (split.length != 2) {
                JOptionPane.showMessageDialog(gui, "Invalid input!");
            }
            emp.setLastname(split[0]);
            emp.setFirstname(split[1]);
            try {
                this.gui.dba.alterFirstLastname(emp);
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                String dateStr = (String) value;
                LocalDate date = LocalDate.parse(dateStr, Employee.dtf);
                emp.setHireDate(date);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(gui, "Wrong date format encountered!\n"
                        + "Format: dd.MM.yyyy");
            }
            try {
                this.gui.dba.alterHiredate(emp);
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.parsedEmployees.get(row)[col] = value;
        fireTableCellUpdated(row, col);
    }
}
