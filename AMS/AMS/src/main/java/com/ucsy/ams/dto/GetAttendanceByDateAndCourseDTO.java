package com.ucsy.ams.dto;

import java.sql.Date;

public class GetAttendanceByDateAndCourseDTO {
    private int id;
    private String name;
    private String email;
    private Date date;
    private int totalAttendance;
    private int currentAttendance;
    private int studentId;
    private String code;
    private String courseName;
    private String semesterName;
    private String mark;

    public GetAttendanceByDateAndCourseDTO(int id, String name, String email, Date date, int totalAttendance, int currentAttendance, int studentId, String code, String courseName, String semesterName) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.date = date;
        this.totalAttendance = totalAttendance;
        this.currentAttendance = currentAttendance;
        this.studentId = studentId;
        this.code = code;
        this.courseName = courseName;
        this.semesterName = semesterName;
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

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
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

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "GetAttendanceByDateAndCourseDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", date=" + date +
                ", totalAttendance=" + totalAttendance +
                ", currentAttendance=" + currentAttendance +
                ", studentId=" + studentId +
                ", code='" + code + '\'' +
                ", courseName='" + courseName + '\'' +
                ", semesterName='" + semesterName + '\'' +
                ", mark='" + mark + '\'' +
                '}';
    }
}
