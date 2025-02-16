package com.ucsy.ams.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ucsy.ams.entity.Attendance;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Integer>{

}
