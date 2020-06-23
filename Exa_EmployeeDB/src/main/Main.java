/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import beans.GenderFilter;
import beans.SortOrder;
import beans.SortType;
import db.EmployeeStatementFactory;
import java.time.LocalDate;

/**
 *
 * @author martin
 */
public class Main {
    public static void main(String[] args) {
        SortType t = SortType.getInstance("NAME", SortOrder.ASC);
        System.out.println(EmployeeStatementFactory.getStatementForInput("db003", LocalDate.MIN, GenderFilter.FEMALE, t));
    }
}
