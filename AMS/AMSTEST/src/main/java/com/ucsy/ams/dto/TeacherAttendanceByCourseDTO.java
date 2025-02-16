package com.ucsy.ams.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;

import java.sql.Date;

public class TeacherAttendanceByCourseDTO {
    @NotNull(message = "Start Date is required")
    @PastOrPresent(message = "Start date must be in the past or present")
    private Date startDate;

    @NotNull(message = "End date is required")
    @PastOrPresent(message = "End date must be in the past or present")
    private Date endDate;

    @Positive(message = "Semester ID must be required")
    private int semId;

    @Positive(message = "Course ID must be a required")
    private int courseId;

    public TeacherAttendanceByCourseDTO() {}

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

    public int getSemId() {
        return semId;
    }

    public void setSemId(int semId) {
        this.semId = semId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
