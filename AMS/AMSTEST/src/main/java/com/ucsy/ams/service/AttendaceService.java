package com.ucsy.ams.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ucsy.ams.entity.Attendance;
import com.ucsy.ams.repository.AttendanceRepo;

@Service
public class AttendaceService {

	private final AttendanceRepo attendanceRepo;

	public AttendaceService(AttendanceRepo attendanceRepo) {
		this.attendanceRepo = attendanceRepo;
	}
	
	public void attendanceSave(List<Attendance> attendance) {
		attendanceRepo.saveAll(attendance);
	}
}
