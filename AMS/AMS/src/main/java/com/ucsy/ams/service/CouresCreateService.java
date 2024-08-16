package com.ucsy.ams.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucsy.ams.dto.CourseCreate;
import com.ucsy.ams.entity.Courses;
import com.ucsy.ams.entity.TimeSlot;
import com.ucsy.ams.repository.CourseRepo;
import com.ucsy.ams.repository.SemesterRepo;
import com.ucsy.ams.repository.TimeSlotRepo;

@Service
public class CouresCreateService {

	@Autowired
	private final CourseRepo couserRepo;

	@Autowired
	private final SemesterRepo semesterRepo;

	@Autowired
	private final TimeSlotRepo timeSlotRepo;

	public CouresCreateService(CourseRepo couserRepo, SemesterRepo semesterRepo, TimeSlotRepo timeSlotRepo) {
		super();
		this.couserRepo = couserRepo;
		this.semesterRepo = semesterRepo;
		this.timeSlotRepo = timeSlotRepo;
	}

	public void createCourse(CourseCreate cc) {

		var course = new Courses();
		course.setCode(cc.getCode());
		course.setCourseName(cc.getCourseName());
		course.setDescription(cc.getDescription());
		couserRepo.save(course);

		var cou = couserRepo.findByCode(cc.getCode());
		var sem = semesterRepo.findById(cc.getId());

		var timeSlot = new TimeSlot();
		timeSlot.setCourse(cou);
		timeSlot.setSemester(sem);
		timeSlot.setDayOfWeek(cc.getDayOfWeek());
		timeSlot.setEndTime(cc.getEndTime());
		timeSlot.setStartTime(cc.getStartTime());

		timeSlotRepo.save(timeSlot);
	}
	
	public void updateCourse(Courses dto) {
		couserRepo.courseUpdate(dto.getCourseName(), dto.getDescription(), dto.getCode());
	}

}
