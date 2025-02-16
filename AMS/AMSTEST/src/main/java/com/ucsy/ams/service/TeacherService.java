package com.ucsy.ams.service;

import com.ucsy.ams.dto.GetAttendanceByDateAndCourseDTO;
import com.ucsy.ams.dto.GetTimeSlotBySemesterIdDTO;
import com.ucsy.ams.repository.AttendanceRepository;
import com.ucsy.ams.repository.TimeSlotRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class TeacherService {
	@Autowired
	private final AttendanceRepository attendanceRepository;

	@Autowired
	private final TimeSlotRepository timeSlotRepository;

	public TeacherService(AttendanceRepository attendanceRepository, TimeSlotRepository timeSlotRepository) {
		this.attendanceRepository = attendanceRepository;
		this.timeSlotRepository = timeSlotRepository;
	}

	public List<GetAttendanceByDateAndCourseDTO> getAttendanceByDateAndCourse(Date startDate, Date endDate, int semId,
			int courseId) {
		return attendanceRepository.findAttendanceByDateandCourse(startDate, endDate, semId, courseId);
	}

	public List<GetTimeSlotBySemesterIdDTO> getTimeSlotByTeacherId(int teacherId) {
		return timeSlotRepository.findTimeSlotByTeacherIdd(teacherId);
	}
}
