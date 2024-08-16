package com.ucsy.ams.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ucsy.ams.entity.Courses;

import jakarta.transaction.Transactional;

@Repository
public interface CourseRepo extends JpaRepository<Courses, Integer> {

	public Courses findByCode(String code);
	
	@Modifying
	@Transactional
	@Query("UPDATE Courses c SET c.courseName = ?1, c.description = ?2 WHERE c.code =?3")
	int courseUpdate(String name, String description, String code);
	
	@Query("select distinct new com.ucsy.ams.entity.Courses(c.id,c.code, c.courseName) from Courses c "
			+ "join TimeSlot t on c.id = t.course.id "
			+ "join Semester s on t.semester.id = s.id where s.semesterName = ?1")
	public List<Courses> findBySemester(String semester);
	
	@Query("select distinct new com.ucsy.ams.entity.Courses(c.id,c.code, c.courseName) from Courses c "
			+ "join TeacherHasCourse th on c.id = th.course.id "
			+ "join Teachers t on th.teacher.id= t.id where t.id = ?1")
	public List<Courses> findByCourseTeacherId(int id);
	
	@Query("select distinct new com.ucsy.ams.entity.Courses(c.id,c.code, c.courseName) from Courses c "
			+ "join Takes th on c.id = th.course.id "
			+ "join Students s on th.student.id= s.id where s.id = ?1")
	public List<Courses> findByCourseStudentId(int id);

	@Query("select distinct s.semesterName from Courses c join TimeSlot t on c.id = t.course.id "
			+ "join Semester s on t.semester.id = s.id "
			+ "join TeacherHasCourse th on th.course.id = c.id "
			+ "join Teachers te on th.teacher.id = te.id where te.id = ?1")
	public List<String>findDistinctSemesterById(int id);
}
