/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import gui.EmployeeModel;
import beans.Department;
import beans.Employee;
import beans.GenderFilter;
import beans.Manager;
import beans.SortOrder;
import beans.SortType;
import db.DB_Access;
import gui.ColumnWrapper;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.table.JTableHeader;
import org.jdatepicker.DateModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

/**
 *
 * @author martin
 */
public class MainGUI extends javax.swing.JFrame {

    private JDatePickerImpl jdp;
    private JComboBox<String> dpCb;

    private JCheckBox cbBirthdate;
    private JCheckBox cbMale;
    private JCheckBox cbFemale;

    public DB_Access dba;

    private List<Department> departments;

    private Department currentDepartment;

    private LocalDate currentBirthDate = LocalDate.now();

    private GenderFilter genderFilter = GenderFilter.BOTH;

    private SortType sortType = SortType.getInstance("NAME", SortOrder.ASC);

    private List<Employee> employees;

    private EmployeeModel elm;

    private List<Manager> managers;

    private DateModel dateModel;

    private ColumnWrapper wrapper = new ColumnWrapper();

    /**
     * Creates new form MainGUI
     */
    public MainGUI() {
        try {
            initComponents();
            this.addCustomComponents();
            this.dba = new DB_Access();
            this.updateDepartments();
            this.loadEmployees();
            this.updateManagers();
            this.elm = new EmployeeModel(this.employees, this);
            this.empTable.setModel(this.elm);
        } catch (SQLException | ClassNotFoundException | FileNotFoundException ex) {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadDepartments() throws SQLException {
        this.departments = this.dba.loadDepartments();
        this.currentDepartment = this.departments.get(0);
    }

    private void updateDepartments() throws SQLException {
        this.loadDepartments();
        this.departments.stream().map(d -> d.toString()).forEach(this.dpCb::addItem);
    }

    private void loadEmployees() throws SQLException {
        this.employees = this.dba.getEmployeesForCriteria(this.currentDepartment.getDeptNo(), this.currentBirthDate, this.genderFilter, this.sortType);
    }

    private void updateEmployees() throws SQLException {
        this.loadEmployees();
        if (this.elm != null) {
            this.elm.setEmployees(this.employees);
        }
    }

    private void loadManagers() throws SQLException {
        this.managers = this.dba.getManagerForCriteria(this.currentDepartment.getDeptNo());
    }

    private void updateManagers() throws SQLException {
        this.loadManagers();
        this.taManagers.setText("<html>" + this.managers.stream().map(Object::toString).collect(Collectors.joining("<br>")) + "</html>");
    }

    private void onDepartmentSelected(java.awt.event.ActionEvent evt) {
        int index = this.dpCb.getSelectedIndex();
        Department newDepartment = this.departments.get(index);
        if (newDepartment != null) {
            try {
                this.currentDepartment = newDepartment;
                this.updateEmployees();
                this.updateManagers();
            } catch (SQLException ex) {
                Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void onGenderSelected(java.awt.event.ActionEvent evt) {
        try {
            boolean m = this.cbMale.isSelected();
            boolean f = this.cbFemale.isSelected();

            if (m && f) {
                this.genderFilter = GenderFilter.BOTH;
            } else if (m) {
                this.genderFilter = GenderFilter.MALE;
            } else {
                this.genderFilter = GenderFilter.FEMALE;
            }

            this.updateEmployees();
        } catch (SQLException ex) {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void onDateChanged(java.awt.event.ActionEvent evt) {
        this.currentBirthDate = this.cbBirthdate.isSelected()
                ? LocalDate.of(this.dateModel.getYear(), this.dateModel.getMonth(), this.dateModel.getDay()) : null;
        try {
            this.updateEmployees();
        } catch (SQLException ex) {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addCustomComponents() {
        this.filterPanel.add(new JLabel("Department"));
        this.dpCb = new JComboBox<>();
        this.filterPanel.add(this.dpCb);
        this.cbBirthdate = new JCheckBox("Birthdate before");
        this.filterPanel.add(this.cbBirthdate);

        this.cbMale = new JCheckBox("Male");
        this.cbFemale = new JCheckBox("Female");

        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        this.jdp = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        this.filterPanel.add(this.jdp);

        this.cbMale.setSelected(true);
        this.cbFemale.setSelected(true);

        this.filterPanel.add(this.cbMale);
        this.filterPanel.add(this.cbFemale);

        this.dpCb.addActionListener(this::onDepartmentSelected);
        this.cbMale.addActionListener(this::onGenderSelected);
        this.cbFemale.addActionListener(this::onGenderSelected);

        this.dateModel = datePanel.getModel();

        datePanel.addActionListener(this::onDateChanged);
        this.cbBirthdate.addActionListener(this::onDateChanged);

        // Add listener for sorting
        JTableHeader header = empTable.getTableHeader();
        header.addMouseListener(new TableHeaderListener());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        filterPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        taManagers = new javax.swing.JEditorPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        empTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 2));

        jPanel1.setLayout(new java.awt.GridLayout(2, 1));

        filterPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Filter"));
        filterPanel.setLayout(new java.awt.GridLayout(3, 2));
        jPanel1.add(filterPanel);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Management"));
        jPanel4.setLayout(new java.awt.GridLayout(1, 1));

        taManagers.setEditable(false);
        taManagers.setContentType("text/html"); // NOI18N
        jScrollPane3.setViewportView(taManagers);

        jPanel4.add(jScrollPane3);

        jPanel1.add(jPanel4);

        getContentPane().add(jPanel1);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Data"));
        jPanel2.setLayout(new java.awt.GridLayout(1, 1));

        empTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(empTable);

        jPanel2.add(jScrollPane1);

        getContentPane().add(jPanel2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable empTable;
    private javax.swing.JPanel filterPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JEditorPane taManagers;
    // End of variables declaration//GEN-END:variables

    public class DateLabelFormatter extends AbstractFormatter {

        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }

    }

    private class TableHeaderListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            Point point = e.getPoint();
            int column = empTable.columnAtPoint(point);
            SortOrder order = wrapper.flipAndFetch(column);

            switch (column) {
                case 0:
                    sortType = SortType.getInstance("NAME", order);
                    break;
                case 1:
                    sortType = SortType.getInstance("GENDER", order);
                    break;
                case 2:
                    sortType = SortType.getInstance("BIRTHDATE", order);
                    break;
                case 3:
                    sortType = SortType.getInstance("HIREDATE", order);
                    break;
            }

            try {
                updateEmployees();
            } catch (SQLException ex) {
                Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
