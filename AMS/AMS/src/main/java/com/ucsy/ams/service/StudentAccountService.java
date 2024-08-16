package com.ucsy.ams.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucsy.ams.dto.StudentAccountDto;
import com.ucsy.ams.repository.AccountRepo;
import com.ucsy.ams.repository.StudentRepo;

@Service
public class StudentAccountService {

	@Autowired
	private final StudentRepo studentRepo;
	
	@Autowired
	private AccountRepo accRepo;

	public StudentAccountService(StudentRepo studentRepo) {
		this.studentRepo = studentRepo;
	}

	public List<StudentAccountDto> getStudentList(String role) {
		return studentRepo.getAllStudentList(role);
	}
	
	public List<StudentAccountDto> getStudentListBySemester(String role, String semester) {
		return studentRepo.getAllStudentListBySemester(role,semester);
	}
	
	public StudentAccountDto getEachStudentById(int id) {
		return studentRepo.getEachStudentById(id);
	}
	
	public void studentUpdate(StudentAccountDto dto) {
		accRepo.accUpdate(dto.getEmail(), "ROLE_STUDENT", dto.getId());
		studentRepo.studentUpdate(dto.getPhoneNo(), dto.getMajor(), dto.getId());
	}
	
	public void deleteStudent(int id) {
		studentRepo.deleteById(id);
		accRepo.deleteById(id);
	}
}
