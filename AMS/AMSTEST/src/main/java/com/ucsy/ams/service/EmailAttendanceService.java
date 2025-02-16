package com.ucsy.ams.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucsy.ams.dto.EmailAttendanceDto;
import com.ucsy.ams.repository.EmailStudentDataRepo;

@Service
public class EmailAttendanceService {

	@Autowired
    private EmailStudentDataRepo studentAttendanceRepo;

    @Autowired
    private EmailService emailService;

    public void notifyStudentsWithLowAttendance() {
        List<EmailAttendanceDto> attendanceDataList = studentAttendanceRepo.findStudentAttendanceData();

        for (EmailAttendanceDto data : attendanceDataList) {
            double attendancePercentage = data.getAttendancePercentage();
            if (attendancePercentage < 75) {
            	String message = "Dear " + data.getName() + ",\n\n" +
                        "Our records show that your attendance for the previous month is " + attendancePercentage + "%, which is below the required 75%.\n\n" +
                        "Student Details:\n" +
                        "- Student ID: " + data.getRollNo() + "\n" +
                        "- Required Attendance: 75%\n" +
                        "- Your Attendance: " + attendancePercentage + "%\n\n" +
                        "Please improve your attendance to avoid disciplinary action. If you need help, contact your teachers or the Student Affairs office.\n\n" +
                        "Thank you.\n\n" +
                        "Best regards,\n" +
                        "Student Affairs Office\n" +
                        "University of Computer Studies, Yangon\n" +
                        "Student Affair: 09443440479";

                try {
                    emailService.sendSimpleMessage(data.getEmail(), "Attendance Warning", message);
                } catch (Exception e) {
    
                    System.err.println("Failed to send email to " + data.getEmail() + ": " + e.getMessage());
                }
            }
        }
    }
}
