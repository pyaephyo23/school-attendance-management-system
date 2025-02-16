package com.ucsy.ams.dto;

import java.util.Date;

public class AttendanceByDateAllRecordDTO {
    private int id;
    private String name;
    private String email;
    private Date date;
    private boolean isPresent;
    private int studentId;
    private String dayOfWeek;
    private String startTime;
    private String endTime;
    private String courseCode;
    private String courseName;
    private String semster;
    private int totalAttendance;
    private int currentAttendance;
    private String percentage;

    public AttendanceByDateAllRecordDTO(int id, String name, String email, Date date, boolean isPresent, int studentId, String dayOfWeek, String startTime, String endTime, String courseCode, String courseName, String semster, int totalAttendance, int currentAttendance) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.date = date;
        this.isPresent = isPresent;
        this.studentId = studentId;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.semster = semster;
        this.totalAttendance = totalAttendance;
        this.currentAttendance = currentAttendance;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
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

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSemster() {
        return semster;
    }

    public void setSemster(String semster) {
        this.semster = semster;
    }

    public int getTotalAttendance() {
        return totalAttendance;
    }

    public void setTotalAttendance(int totalAttendance) {
        this.totalAttendance = totalAttendance;
    }

    public int getCurrentAttendance() {
        return currentAttendance;
    }

    public void setCurrentAttendance(int currentAttendance) {
        this.currentAttendance = currentAttendance;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }


    @Override
    public String toString() {
        return "AttendanceByDateAllRecordDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", date=" + date +
                ", isPresent=" + isPresent +
                ", studentId=" + studentId +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", courseName='" + courseName + '\'' +
                ", semster='" + semster + '\'' +
                ", totalAttendance=" + totalAttendance +
                ", currentAttendance=" + currentAttendance +
                '}';
    }
}
