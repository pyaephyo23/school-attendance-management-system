package com.ucsy.ams.service;

import com.ucsy.ams.dto.AttendanceDashboardDTO;
import com.ucsy.ams.repository.AttendanceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
	@Autowired
	private final AttendanceRepository attendanceRepository;

	public AdminService(AttendanceRepository attendanceRepository) {
		this.attendanceRepository = attendanceRepository;
	}

	public List<AttendanceDashboardDTO> getAttendanceBySemester() {
		return attendanceRepository.findAttendanceBySemester();
	}
}
