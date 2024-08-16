package com.ucsy.ams.dto;

import java.sql.Date;

public class CourseSemesterTimeSlotDto {

	private int id;

	private String semesterName;

	private Date startDate;

	private Date endDate;

	private String code;

	private String courseName;

	private String description;

	private String startTime;

	private String endTime;

	private String dayOfWeek;

	private int semesterId;

	public CourseSemesterTimeSlotDto() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSemesterName() {
		return semesterName;
	}

	public void setSemesterName(String semesterName) {
		this.semesterName = semesterName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public int getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(int semesterId) {
		this.semesterId = semesterId;
	}

	public CourseSemesterTimeSlotDto(int id, String semesterName, Date startDate, Date endDate, String code,
			String courseName, String description, String startTime, String endTime, String dayOfWeek) {
		super();
		this.id = id;
		this.semesterName = semesterName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.code = code;
		this.courseName = courseName;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.dayOfWeek = dayOfWeek;
	}

	public CourseSemesterTimeSlotDto(int id, String semesterName, Date startDate, Date endDate, String code,
			String courseName, String description, String startTime, String endTime, String dayOfWeek, int semesterId) {
		super();
		this.id = id;
		this.semesterName = semesterName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.code = code;
		this.courseName = courseName;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.dayOfWeek = dayOfWeek;
		this.semesterId = semesterId;
	}

	@Override
	public String toString() {
		return "CourseSemesterTimeSlotDto [id=" + id + ", semesterName=" + semesterName + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", code=" + code + ", courseName=" + courseName + ", description="
				+ description + ", startTime=" + startTime + ", endTime=" + endTime + ", dayOfWeek=" + dayOfWeek + "]";
	}

}
