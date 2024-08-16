package com.ucsy.ams.entity;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "account")
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name", nullable = false, length = 45)
	private String name;

	@Column(name = "role", nullable = false, length = 45)
	private String role;

	@Column(name = "email", nullable = false, length = 45, unique = true)
	private String email;

	@Column(name = "password", nullable = false, length = 255)
	private String password;

	@OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
	private Students students;

	@OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
	private Teachers teachers;

	public Account() {
		// TODO Auto-generated constructor stub
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", name=" + name + ", role=" + role + ", email=" + email + ", password=" + password
				+ ", students=" + students + ", teachers=" + teachers + "]";
	}

}
