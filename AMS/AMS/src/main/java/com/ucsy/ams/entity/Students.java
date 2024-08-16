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
@Table(name = "students")
public class Students implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name = "rollNo", nullable = false, unique = true, length = 45)
	private String rollNo;

	@Column(name = "nrcNo", nullable = false, unique = true, length = 45)
	private String nrcNo;

	@Column(name = "dob", nullable = false, length = 45)
	private Date dob;

	@Column(name = "phoneNo", nullable = false, length = 45)
	private String phoneNo;

	@Column(name = "major", nullable = false, length = 45)
	private String major;

	@Column(name = "registerDate", nullable = false, length = 45)
	private Date registerDate;

	@Column(name = "semester", nullable = false, length = 45)
	private String semester;

	@OneToOne(optional = false)
	@PrimaryKeyJoinColumn
	private Account account;

	public Students() {
		// TODO Auto-generated constructor stub
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
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
}
