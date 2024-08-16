package com.ucsy.ams.dto;

import java.sql.Date;

public class StudentAccountDto {

	private int id;
	private String name;
	private String email;
	private String rollNo;
	private String nrcNo;
	private Date dob;
	private String phoneNo;
	private String major;
	private Date registerDate;
	private String semester;
	private String password;

	public StudentAccountDto() {
		// TODO Auto-generated constructor stub
	}

	public StudentAccountDto(int id, String name, String email, String rollNo, String nrcNo, Date dob, String phoneNo,
			String major, Date registerDate, String semester) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.rollNo = rollNo;
		this.nrcNo = nrcNo;
		this.dob = dob;
		this.phoneNo = phoneNo;
		this.major = major;
		this.registerDate = registerDate;
		this.semester = semester;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public String getNrcNo() {
		return nrcNo;
	}

	public void setNrcNo(String nrcNo) {
		this.nrcNo = nrcNo;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

}
