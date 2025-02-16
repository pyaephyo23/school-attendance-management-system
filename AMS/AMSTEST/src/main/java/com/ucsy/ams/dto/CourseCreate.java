package com.ucsy.ams.dto;

public class CourseCreate {

	private int id;

	private String code;

	private String courseName;

	private String description;

	private String startTime;

	private String endTime;

	private String dayOfWeek;

	public CourseCreate() {
		// TODO Auto-generated constructor stub
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CourseCreate(int id, String code, String courseName, String description, String startTime, String endTime,
			String dayOfWeek) {
		super();
		this.id = id;
		this.code = code;
		this.courseName = courseName;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.dayOfWeek = dayOfWeek;
	}

}
