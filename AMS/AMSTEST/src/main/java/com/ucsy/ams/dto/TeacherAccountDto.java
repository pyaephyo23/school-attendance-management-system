package com.ucsy.ams.dto;

import java.sql.Date;

public class TeacherAccountDto {

	private int id;
	private String name;
	private String email;
	private String role;
	private String nrcNo;
	private Date dob;
	private String phoneNo;
	private String teacherRank;
	private String highestDegree;
	private String faculty;
	private Date registerDate;
	private String password;
	private String semester;

	public TeacherAccountDto() {
		// TODO Auto-generated constructor stub
	}

	public TeacherAccountDto(int id, String name, String email, String role, String nrcNo, Date dob, String phoneNo,
			String teacherRank, String highestDegree, String faculty, Date registerDate,
			String semester) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.role = role;
		this.nrcNo = nrcNo;
		this.dob = dob;
		this.phoneNo = phoneNo;
		this.teacherRank = teacherRank;
		this.highestDegree = highestDegree;
		this.faculty = faculty;
		this.registerDate = registerDate;
		this.semester = semester;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getTeacherRank() {
		return teacherRank;
	}

	public void setTeacherRank(String teacherRank) {
		this.teacherRank = teacherRank;
	}

	public String getHighestDegree() {
		return highestDegree;
	}

	public void setHighestDegree(String highestDegree) {
		this.highestDegree = highestDegree;
	}

	public String getFaculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	@Override
	public String toString() {
		return "TeacherAccountDto [id=" + id + ", name=" + name + ", email=" + email + ", role=" + role + ", nrcNo="
				+ nrcNo + ", dob=" + dob + ", phoneNo=" + phoneNo + ", teacherRank=" + teacherRank + ", highestDegree="
				+ highestDegree + ", faculty=" + faculty + ", registerDate=" + registerDate + ", password=" + password
				+ "]";
	}

}