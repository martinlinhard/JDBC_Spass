/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import beans.Employee;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.AbstractListModel;

/**
 *
 * @author martin
 */
public class EmployeeListModel extends AbstractListModel<Employee> {

    private List<Employee> allEmployees;
    private List<Employee> currentEmployees;
    private String filter = "Alle";

    public EmployeeListModel(List<Employee> allEmployees) {
        this.allEmployees = allEmployees;
        this.currentEmployees = new ArrayList<>();
        this.currentEmployees.addAll(allEmployees);
    }

    @Override
    public int getSize() {
        return this.currentEmployees.size();
    }

    @Override
    public Employee getElementAt(int i) {
        return this.currentEmployees.get(i);
    }

    public Set<Integer> getDepartments() {
        Set<Integer> departments = new HashSet<>();
        for (Employee e : this.allEmployees) {
            departments.add(e.getAbtNR());
        }
        return departments;
    }

    public void setFilter(String filter) {
        this.filter = filter;
        this.filterDepartments();
    }

    public void filterDepartments() {
        this.currentEmployees.clear();
        if (this.filter.equals("Alle")) {
            this.currentEmployees.addAll(allEmployees);
        } else {
            int dep = Integer.parseInt(filter);
            for (Employee e : this.allEmployees) {
                if (e.getAbtNR() == dep) {
                    this.currentEmployees.add(e);
                }
            }
        }
        this.fireContentsChanged(this, 0, this.allEmployees.size());
    }
    
    public List<Employee> getCurrentEmps() {
        return this.currentEmployees;
    }
    
    public void addEmployee(Employee e) {
        this.allEmployees.add(e);
        this.filterDepartments();
    }
    
    public void removeEmployee(Employee e) {
        this.allEmployees.remove(e);
        this.filterDepartments();
    }
}
