package com.ucsy.ams.repository;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ucsy.ams.entity.Semester;

import jakarta.transaction.Transactional;

@Repository
public interface SemesterRepo extends JpaRepository<Semester, Integer> {

	public Semester findById(int id);

	@Modifying
	@Transactional
	@Query("UPDATE Semester s SET s.startDate = ?1, s.endDate= ?2 WHERE id =?3")
	int updateSem(Date startDate, Date endDate, int id);

	public Semester findBySemesterName(String semester);
	
	
}
