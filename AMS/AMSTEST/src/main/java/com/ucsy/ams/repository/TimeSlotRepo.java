package com.ucsy.ams.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ucsy.ams.dto.CourseSemesterTimeSlotDto;
import com.ucsy.ams.entity.Attendance;
import com.ucsy.ams.entity.TimeSlot;

import jakarta.transaction.Transactional;

@Repository
public interface TimeSlotRepo extends JpaRepository<TimeSlot, Integer> {

	@Query("select new com.ucsy.ams.dto.CourseSemesterTimeSlotDto(t.id, s.semesterName, s.startDate, s.endDate, c.code"
			+ ", c.courseName, c.description, t.startTime, t.endTime, t.dayOfWeek) from TimeSlot t join Semester s "
			+ "on t.semester.id = s.id join Courses c on t.course.id = c.id")
	List<CourseSemesterTimeSlotDto> getAllCourseDetail();

	@Query("select new com.ucsy.ams.dto.CourseSemesterTimeSlotDto(t.id, s.semesterName, s.startDate, s.endDate, c.code"
			+ ", c.courseName, c.description, t.startTime, t.endTime, t.dayOfWeek, t.semester.id) from TimeSlot t join Semester s "
			+ "on t.semester.id = s.id join Courses c on t.course.id = c.id where t.id = ?1")
	CourseSemesterTimeSlotDto getEachCourseDetail(int id);

	@Modifying
	@Transactional
	@Query("UPDATE TimeSlot t SET t.startTime = ?1, t.endTime= ?2 , t.dayOfWeek = ?3 WHERE t.id =?4")
	int timeSlotUpdate(String startTime, String endTime, String dayOfWeek, int id);

	@Query("select new com.ucsy.ams.dto.CourseSemesterTimeSlotDto(t.id, s.semesterName, s.startDate, s.endDate, c.code"
			+ ", c.courseName, c.description, t.startTime, t.endTime, t.dayOfWeek, t.semester.id) from TimeSlot t join Semester s "
			+ "on t.semester.id = s.id join Courses c on t.course.id = c.id "
			+ "join TeacherHasCourse h on h.course.id = c.id "
			+ "join Teachers th on h.teacher.id = th.id where th.id = ?1")
	List<CourseSemesterTimeSlotDto> getCourseForEachTeacherDetail(int id);
	
	@Query("select new com.ucsy.ams.dto.CourseSemesterTimeSlotDto(t.id, s.semesterName, s.startDate, s.endDate, c.code"
			+ ", c.courseName, c.description, t.startTime, t.endTime, t.dayOfWeek, t.semester.id) from TimeSlot t join Semester s "
			+ "on t.semester.id = s.id join Courses c on t.course.id = c.id "
			+ "join Takes tak on tak.course.id = c.id "
			+ "join Students st on tak.student.id = st.id where st.id = ?1")
	List<CourseSemesterTimeSlotDto> getCourseForEachStudentDetail(int id);
	
	
	@Query("select distinct t.id from Courses c join TimeSlot t on c.id = t.course.id "
			+ "join Semester s on t.semester.id = s.id "
			+ "join TeacherHasCourse th on th.course.id = c.id "
			+ "join Teachers te on th.teacher.id = te.id where te.id =?1 and t.dayOfWeek= ?2 and t.course.id = ?3")
	public List<Integer> getTimeSlotId(int teacherId, String dayOfWeek ,int id);

	void save(List<Attendance> attent);

}
