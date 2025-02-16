package com.ucsy.ams.dto;

public class AttendanceDashboardDTO {
    private int totalAttendance;
    private int currentAttendance;
    private String semesterName;

    public AttendanceDashboardDTO(int totalAttendance, int currentAttendance, String semesterName) {
        this.totalAttendance = totalAttendance;
        this.currentAttendance = currentAttendance;
        this.semesterName = semesterName;
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

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    @Override
    public String toString() {
        return "AttendanceDashboardDTO{" +
                "totalAttendance=" + totalAttendance +
                ", currentAttendance=" + currentAttendance +
                ", semesterName='" + semesterName + '\'' +
                '}';
    }
}
