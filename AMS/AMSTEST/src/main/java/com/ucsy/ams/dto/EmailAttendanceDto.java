package com.ucsy.ams.dto;

public class EmailAttendanceDto {
	private int id;
    private String name;
    private String email;
    private String rollNo;
    private long totalClasses;
    private long attendedClasses;

    public EmailAttendanceDto(int id, String name, String email, String rollNo, long totalClasses, long attendedClasses) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.rollNo = rollNo;
        this.totalClasses = totalClasses;
        this.attendedClasses = attendedClasses;
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


	public String getRollNo() {
		return rollNo;
	}


	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}


	public long getTotalClasses() {
		return totalClasses;
	}


	public void setTotalClasses(long totalClasses) {
		this.totalClasses = totalClasses;
	}


	public long getAttendedClasses() {
		return attendedClasses;
	}


	public void setAttendedClasses(long attendedClasses) {
		this.attendedClasses = attendedClasses;
	}


	public double getAttendancePercentage() {
        return ((double) attendedClasses / totalClasses) * 100;
    }

}
