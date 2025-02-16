package com.ucsy.ams.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ucsy.ams.dto.EmailAttendanceDto;

@Repository
public interface EmailStudentDataRepo {

	List<EmailAttendanceDto> findStudentAttendanceData();
}
