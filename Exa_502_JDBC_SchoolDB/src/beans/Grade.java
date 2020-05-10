/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author martin
 */
public class Grade {

    private int classid;
    private String className;

    public Grade(int classid, String className) {
        this.classid = classid;
        this.className = className;
    }

    public static List<Grade> parse(ResultSet s) throws SQLException {
        List<Grade> grades = new ArrayList<>();
        while (s.next()) {
            //we only ever should get one result
            grades.add(new Grade(s.getInt("classid"), s.getString("classname")));
        }
        return grades;
    }

    public Integer getClassid() {
        return new Integer(this.classid);
    }

    public String getClassName() {
        return className;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.className);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Grade other = (Grade) obj;
        if (!Objects.equals(this.className, other.className)) {
            return false;
        }
        return true;
    }

    public static Map<String, Integer> toClassMappings(Collection<Grade> grades) {
        return grades.stream().collect(Collectors.toMap(Grade::getClassName, Grade::getClassid));
    }

    @Override
    public String toString() {
        return "Grade{" + "classid=" + classid + ", className=" + className + '}';
    }

    public void setClassid(int classid) {
        this.classid = classid;
    }

}