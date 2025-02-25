package com.ucsy.ams;

import com.ucsy.ams.dto.EmailAttendanceDto;
import com.ucsy.ams.repository.EmailStudentDataRepo;
import com.ucsy.ams.service.EmailAttendanceService;
import com.ucsy.ams.service.EmailService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmailAttendanceServiceTest {

    @InjectMocks
    private EmailAttendanceService emailAttendanceService;

    @Mock
    private EmailStudentDataRepo emailStudentDataRepo;

    @Mock
    private EmailService emailService;

    private List<EmailAttendanceDto> attendanceDataList;

    @BeforeEach
    public void setUp() {
        // Test data
        attendanceDataList = Arrays.asList(
                new EmailAttendanceDto(1, "John Doe", "john.doe@example.com", "12345", 20, 10),  // Low attendance (50%)
                new EmailAttendanceDto(2, "Jane Smith", "jane.smith@example.com", "12346", 20, 18) // Sufficient attendance (90%)
        );
    }

    @Test
    public void testEmailSentForLowAttendance() {
        // Arrange
        when(emailStudentDataRepo.findStudentAttendanceData()).thenReturn(attendanceDataList);

        // Expected message for John Doe (attendance < 75%)
        String expectedMessageForJohn = "Dear John Doe,\n\n" +
                "Our records show that your attendance for the previous month is 50.0%, which is below the required 75%.\n\n" +
                "Student Details:\n" +
                "- Student ID: 12345\n" +
                "- Required Attendance: 75%\n" +
                "- Your Attendance: 50.0%\n\n" +
                "Please improve your attendance to avoid disciplinary action. If you need help, contact your teachers or the Student Affairs office.\n\n" +
                "Thank you.\n\n" +
                "Best regards,\n" +
                "Student Affairs Office\n" +
                "University of Computer Studies, Yangon\n" +
                "Student Affair: 09443440479";

        // Act
        emailAttendanceService.notifyStudentsWithLowAttendance();

        // Assert
        // Verify that the email service sends an email for John Doe (attendance < 75%)
        verify(emailService, times(1)).sendSimpleMessage(
                Mockito.eq("john.doe@example.com"),
                Mockito.eq("Attendance Warning"),
                Mockito.eq(expectedMessageForJohn)
        );
    }

    @Test
    public void testEmailNotSentForSufficientAttendance() {
        // Arrange
        when(emailStudentDataRepo.findStudentAttendanceData()).thenReturn(attendanceDataList);

        // Act
        emailAttendanceService.notifyStudentsWithLowAttendance();

        // Assert
        // Verify that the email service does NOT send an email for Jane Smith (attendance >= 75%)
        verify(emailService, times(0)).sendSimpleMessage(
                Mockito.eq("jane.smith@example.com"),
                Mockito.eq("Attendance Warning"),
                Mockito.contains("Your attendance is below the required 75%")
        );
    }
}
