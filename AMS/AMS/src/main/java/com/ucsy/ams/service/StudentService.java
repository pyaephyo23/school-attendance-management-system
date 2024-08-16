package com.ucsy.ams.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucsy.ams.dto.GetTimeSlotBySemesterIdDTO;
import com.ucsy.ams.dto.StudentRollCallDTO;
import com.ucsy.ams.repository.AttendanceRepository;
import com.ucsy.ams.repository.TimeSlotRepository;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private final TimeSlotRepository timeSlotRepository;
    @Autowired
    private final AttendanceRepository attendanceRepository;

    public StudentService(TimeSlotRepository timeSlotRepository, AttendanceRepository attendanceRepository) {
        this.timeSlotRepository = timeSlotRepository;
        this.attendanceRepository = attendanceRepository;
    }

    public List<GetTimeSlotBySemesterIdDTO> getTimeSlotBySemesterId(String semesterName, int studentId) {
        return timeSlotRepository.findTimeSlotBySemesterId(semesterName, studentId);
    }
    
    public List<StudentRollCallDTO> findAttendanceByStudentAndCourse(String startDate, String endDate, int studentId, String semId){
        return attendanceRepository.findAttendanceByStudentAndCourse(startDate, endDate, studentId, semId);
    }
}
