package com.ucsy.ams.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucsy.ams.entity.Semester;
import com.ucsy.ams.repository.SemesterRepo;

@Service
public class SemesterService {

	@Autowired
	private final SemesterRepo semesterRepo;

	public SemesterService(SemesterRepo semesterRepo) {
		this.semesterRepo = semesterRepo;
	}

	public List<Semester> getAllSemesters() {
		return semesterRepo.findAll();
	}

	public List<Integer> getAllSemesterId() {
		return semesterRepo.findAll().stream().map(Semester::getId).collect(Collectors.toList());
	}

}
