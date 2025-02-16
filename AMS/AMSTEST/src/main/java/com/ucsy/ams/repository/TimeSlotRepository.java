package com.ucsy.ams.repository;

import org.springframework.stereotype.Repository;

import com.ucsy.ams.dto.GetTimeSlotBySemesterIdDTO;

import java.util.List;

@Repository
public interface TimeSlotRepository {
    public List<GetTimeSlotBySemesterIdDTO> findTimeSlotByTeacherIdd(int studentId);

    public List<GetTimeSlotBySemesterIdDTO> findTimeSlotBySemesterId(String semesterName, int studentId);
}
