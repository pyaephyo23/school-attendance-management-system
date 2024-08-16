package com.ucsy.ams.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ucsy.ams.dto.TeacherAccountDto;
import com.ucsy.ams.entity.Teachers;

import jakarta.transaction.Transactional;

@Repository
public interface TeacherAccountRepo extends JpaRepository<Teachers, Integer> {

	@Query("select new com.ucsy.ams.dto.TeacherAccountDto(t.id,a.name,a.email,a.role, t.nrcNo, t.dob, t.phoneNo,"
			+ "t.teacherRank,t.highestDegree, t.faculty, t.registerDate , t.semester) "
			+ "from Teachers t join Account a on t.id = a.id where a.role = ?1")
	List<TeacherAccountDto> getAllTeacherList(String role);
	
	@Query("select new com.ucsy.ams.dto.TeacherAccountDto(t.id,a.name,a.email,a.role,t.nrcNo, t.dob, t.phoneNo,"
			+ "t.teacherRank,t.highestDegree, t.faculty, t.registerDate, t.semester) "
			+ "from Teachers t join Account a on t.id = a.id where a.id = ?1")
	TeacherAccountDto getTeacherById(int id);
	
	@Modifying
	@Transactional
	@Query("UPDATE Teachers t SET t.phoneNo = ?1, t.teacherRank = ?2 , t.highestDegree = ?3,"
			+ "t.faculty = ?4, t.semester = ?5 WHERE id =?6")
	int teacherUpdate(String phoneNo, String teacherRank,String highestDegree,String faculty, 
			String semester,int id);

}
