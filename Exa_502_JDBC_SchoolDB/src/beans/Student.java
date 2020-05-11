/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author martin
 */
public class Student implements Comparable<Student> {

    private int studentID;
    private String classname;
    private int catno;
    private String firstname;
    private String lastname;
    private String gender;
    private LocalDate dateOfBirth;

    public static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Student(int studentID, String classname, int catno, String firstname, String lastname, String gender, LocalDate dateOfBirth) {
        this.studentID = studentID;
        this.classname = classname;
        this.catno = catno;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public int getCatno() {
        return catno;
    }

    public void setCatno(int catno) {
        this.catno = catno;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getlastname() {
        return lastname;
    }

    public void setlastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    private static Student fromResultSet(ResultSet set) throws SQLException {
        int studentID = set.getInt("studentid");
        String classname = set.getString("classname");
        int catno = set.getInt("catno");
        String firstname = set.getString("firstname");
        String lastname = set.getString("lastname");
        String gender = set.getString("gender");
        LocalDate birthDate = set.getDate("dateofbirth").toLocalDate();

        return new Student(studentID, classname, catno, firstname, lastname, gender, birthDate);
    }

    public static List<Student> parseFromResultSet(ResultSet set) throws SQLException {
        List<Student> students = new ArrayList<>();
        while (set.next()) {
            students.add(Student.fromResultSet(set));
        }
        return students;
    }

    public static Student fromLine(String line) {
        /*
            Klasse;Familienname;Vorname;Geschlecht;Geburtsdatum
            3AHIF;ADAM;Florian;m;2003-03-14
            3AHIF;ALDRIAN;Dominik;m;2003-04-14
            3AHIF;ANGERBAUER;Matthias;m;2002-12-19
            3AHIF;EISLER;Theresa;w;2001-12-05
            3AHIF;FINA;Marcel;m;2002-06-04
            3AHIF;GOSCHIER;Annika;w;2003-02-05
         */

        String[] tokens = line.split(";");
        return new Student(0, tokens[0], -1, tokens[2], tokens[1], tokens[3], LocalDate.parse(tokens[4], Student.DTF));
    }

    public static Set<Grade> getGradesForStudents(List<Student> students) {
        return students.stream().map(s -> {
            return new Grade(0, s.getClassname());
        }).collect(Collectors.toSet());
    }

    public static HashMap<String, List<Student>> splitIntoClasses(List<Student> students) {
        HashMap<String, List<Student>> values = new HashMap<>();

        students.forEach((s) -> {
            if (values.containsKey(s.getClassname())) {
                //class already present --> just append student
                List<Student> prev = values.get(s.getClassname());
                prev.add(s);
                Collections.sort(prev);
            } else {
                //class not yet present --> create a new entry
                values.put(s.getClassname(), new ArrayList<Student>() {
                    {
                        add(s);
                    }
                });
            }
        });

        return values;
    }

    public static List<Student> mergeClasses(Map<String, List<Student>> students) {
        List<Student> newStudents = new ArrayList<>();
        for (List<Student> l : students.values()) {
            newStudents.addAll(l);
        }
        return newStudents;
    }

    @Override
    public String toString() {
        return String.format("%d;%s;%s;%s;%s;%s", studentID, classname.toUpperCase(), lastname.toUpperCase(), firstname, gender.charAt(0) + "", DTF.format(dateOfBirth));
    }

    public String toCSVString() {
        //Klasse;Familienname;Vorname;Geschlecht;Geburtsdatum
        return String.format("%s;%s;%s;%s;%s", this.classname, this.lastname, this.firstname, this.gender, Student.DTF.format(this.dateOfBirth));
    }

    @Override
    public int compareTo(Student t) {
        if (this.lastname.equals(t.lastname)) {
            return this.firstname.compareToIgnoreCase(t.firstname);
        } else {
            return this.lastname.compareToIgnoreCase(t.lastname);
        }
    }
}
