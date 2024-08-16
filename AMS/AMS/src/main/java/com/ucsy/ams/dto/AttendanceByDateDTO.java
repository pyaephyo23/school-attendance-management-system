package com.ucsy.ams.dto;

import java.sql.Date;

public class AttendanceByDateDTO {
    private Date startDate;
    private Date endDate;
    private int semId;

    public AttendanceByDateDTO() {

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

    public int getSemId() {
        return semId;
    }

    public void setSemId(int semId) {
        this.semId = semId;
    }
}
