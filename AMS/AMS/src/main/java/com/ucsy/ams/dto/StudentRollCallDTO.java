package com.ucsy.ams.dto;

public class StudentRollCallDTO {
    String name;
    int totalAttendance;
    int currentAttendance;
    String code;
    String courseName;
    String semester;
    String percentage;
    String mark;

    public StudentRollCallDTO(String name, int totalAttendance, int currentAttendance, String code, String courseName, String semester) {
        this.name = name;
        this.totalAttendance = totalAttendance;
        this.currentAttendance = currentAttendance;
        this.code = code;
        this.courseName = courseName;
        this.semester = semester;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "StudentRollCallDTO{" +
                "name='" + name + '\'' +
                ", totalAttendance=" + totalAttendance +
                ", currentAttendance=" + currentAttendance +
                ", code='" + code + '\'' +
                ", courseName='" + courseName + '\'' +
                ", semester='" + semester + '\'' +
                ", percentage=" + percentage +
                ", mark=" + mark +
                '}';
    }
}
