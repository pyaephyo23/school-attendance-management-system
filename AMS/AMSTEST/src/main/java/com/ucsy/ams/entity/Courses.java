package com.ucsy.ams.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "courses")
public class Courses implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "code", nullable = false, unique = true, length = 45)
	private String code;

	@Column(name = "courseName", nullable = false, length = 45)
	private String courseName;

	@Column(name = "description", nullable = true, length = 45)
	private String description;

	public Courses() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Courses [id=" + id + ", code=" + code + ", courseName=" + courseName + ", description=" + description
				+ "]";
	}

	public Courses(int id, String code, String courseName) {
		super();
		this.id = id;
		this.code = code;
		this.courseName = courseName;
	}
	
}
