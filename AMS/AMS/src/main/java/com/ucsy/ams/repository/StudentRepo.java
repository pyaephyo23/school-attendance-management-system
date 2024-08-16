package com.ucsy.ams.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ucsy.ams.dto.StudentAccountDto;
import com.ucsy.ams.entity.Students;

import jakarta.transaction.Transactional;

@Repository
public interface StudentRepo extends JpaRepository<Students, Integer> {

	@Query("select new com.ucsy.ams.dto.StudentAccountDto(s.id,a.name,a.email,s.rollNo, s.nrcNo, s.dob,"
			+ "s.phoneNo,s.major, s.registerDate, s.semester) "
			+ "from Students s join Account a on s.id = a.id where a.role = ?1")
	List<StudentAccountDto> getAllStudentList(String role);
	
	@Query("select new com.ucsy.ams.dto.StudentAccountDto(s.id,a.name,a.email,s.rollNo, s.nrcNo, s.dob,"
			+ "s.phoneNo,s.major, s.registerDate, s.semester) "
			+ "from Students s join Account a on s.id = a.id where a.role = ?1 and s.semester = ?2")
	List<StudentAccountDto> getAllStudentListBySemester(String role,String semester);

	@Query("select new com.ucsy.ams.dto.StudentAccountDto(s.id,a.name,a.email,s.rollNo, s.nrcNo, s.dob,"
			+ "s.phoneNo,s.major, s.registerDate, s.semester) "
			+ "from Students s join Account a on s.id = a.id where a.id = ?1")
	StudentAccountDto getEachStudentById(int id);

	@Modifying
	@Transactional
	@Query("UPDATE Students s SET s.phoneNo = ?1,s.major =?2 WHERE id =?3")
	int studentUpdate(String phnoeNo,String major, int id);

	Optional<Students> findBySemester(String string);
	
	@Query("select new com.ucsy.ams.dto.StudentAccountDto(s.id,a.name,a.email,s.rollNo, s.nrcNo, s.dob,"
			+ "s.phoneNo,s.major, s.registerDate, s.semester) "
			+ "from Students s join Account a on s.id = a.id "
			+ "join Takes t on t.student.id = s.id "
			+ "join Courses c on t.course.id = c.id where c.id = ?1")
	List<StudentAccountDto> getAllStudentListByCourseId(int cid);

}
