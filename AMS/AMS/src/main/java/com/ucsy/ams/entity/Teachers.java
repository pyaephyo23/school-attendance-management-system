package com.ucsy.ams.entity;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "teachers")
public class Teachers implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name = "nrcNo", nullable = false, unique = true, length = 45)
	private String nrcNo;

	@Column(name = "dob", nullable = false, length = 45)
	private Date dob;

	@Column(name = "phoneNo", nullable = false, length = 45)
	private String phoneNo;

	@Column(name = "teacherRank", nullable = false, length = 45)
	private String teacherRank;

	@Column(name = "highestDegree", nullable = false, length = 45)
	private String highestDegree;

	@Column(name = "faculty", nullable = false, length = 255)
	private String faculty;

	@Column(name = "registerDate", nullable = false, length = 45)
	private Date registerDate;
	
	@Column(name = "semester", nullable = true, length = 45)
	private String semester;

	@OneToOne(optional = false)
	@PrimaryKeyJoinColumn
	private Account account;

	public Teachers() {
		// TODO Auto-generated constructor stub
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
		this.id = account.getId();
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

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

}
