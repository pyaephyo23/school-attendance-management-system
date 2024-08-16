package com.ucsy.ams.dto;



public class GetTimeSlotBySemesterIdDTO {
    private int day_of_week;
    private String start_time;
    private  String end_time;
    private String course_name;
    private int teaches_id;
    private String teacher_name;
    
	public GetTimeSlotBySemesterIdDTO(int day_of_week, String start_time, String end_time, String course_name,
			int teaches_id) {
		super();
		this.day_of_week = day_of_week;
		this.start_time = start_time;
		this.end_time = end_time;
		this.course_name = course_name;
		this.teaches_id = teaches_id;
	}
	public GetTimeSlotBySemesterIdDTO(int day_of_week, String start_time, String end_time, String course_name,
			int teaches_id, String teacher_name) {
		super();
		this.day_of_week = day_of_week;
		this.start_time = start_time;
		this.end_time = end_time;
		this.course_name = course_name;
		this.teaches_id = teaches_id;
		this.teacher_name = teacher_name;
	}
	public int getDay_of_week() {
		return day_of_week;
	}
	public void setDay_of_week(int day_of_week) {
		this.day_of_week = day_of_week;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public int getTeaches_id() {
		return teaches_id;
	}
	public void setTeaches_id(int teaches_id) {
		this.teaches_id = teaches_id;
	}
	public String getTeacher_name() {
		return teacher_name;
	}
	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}
	@Override
	public String toString() {
		return "GetTimeSlotBySemesterIdDTO [day_of_week=" + day_of_week + ", start_time=" + start_time + ", end_time="
				+ end_time + ", course_name=" + course_name + ", teaches_id=" + teaches_id + ", teacher_name="
				+ teacher_name + "]";
	}

}
