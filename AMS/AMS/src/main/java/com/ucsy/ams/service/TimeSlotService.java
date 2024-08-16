package com.ucsy.ams.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ucsy.ams.dto.CourseSemesterTimeSlotDto;
import com.ucsy.ams.repository.TimeSlotRepo;

@Service
public class TimeSlotService {

	private final TimeSlotRepo timeSlotRepo;

	public TimeSlotService(TimeSlotRepo timeSlotRepo) {
		this.timeSlotRepo = timeSlotRepo;
	}
	
	public List<CourseSemesterTimeSlotDto> getAllTime(){
		return timeSlotRepo.getAllCourseDetail();
	}
}
