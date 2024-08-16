package com.ucsy.ams.service;

import com.ucsy.ams.dto.AttendanceByDateAllRecordDTO;
import com.ucsy.ams.repository.AttendanceRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class ClassRoasterService {
	@Autowired
	private final AttendanceRepository attendanceRepository;

	public ClassRoasterService(AttendanceRepository attendanceRepository) {
		this.attendanceRepository = attendanceRepository;
	}

	public List<AttendanceByDateAllRecordDTO> getAttendanceByDate(Date startDate, Date endDate, int semId) {
		return attendanceRepository.findAttendanceByDateAndSemester(startDate, endDate, semId);
	}
}
