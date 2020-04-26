package exa_jdbc.beans;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Student {
	private int studentId;
	private int catNR;
	private String firstname;
	private String lastname;
	private LocalDate birthdate;
	public static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");

	public Student() {
	}

	public Student(int studentId, int catNR, String firstname, String lastname, LocalDate birthdate) {
		this.studentId = studentId;
		this.catNR = catNR;
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthdate = birthdate;
	}

	@Override
	public String toString() {
		return "Student [StudentId=" + studentId + ", birthdate=" + birthdate + ", catNR=" + catNR + ", firstname="
				+ firstname + ", lastname=" + lastname + "]";
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getCatNR() {
		return catNR;
	}

	public void setCatNR(int catNR) {
		this.catNR = catNR;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}
}
