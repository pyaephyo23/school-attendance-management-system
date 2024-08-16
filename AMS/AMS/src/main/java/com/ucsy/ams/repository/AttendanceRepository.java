package com.ucsy.ams.repository;

import com.ucsy.ams.dto.AttendanceByDateAllRecordDTO;
import com.ucsy.ams.dto.AttendanceDashboardDTO;
import com.ucsy.ams.dto.GetAttendanceByDateAndCourseDTO;
import com.ucsy.ams.dto.StudentRollCallDTO;

import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface AttendanceRepository {

   public List<AttendanceByDateAllRecordDTO> findAttendanceByDateAndSemester(Date start, Date end, int semId);

   public List<GetAttendanceByDateAndCourseDTO> findAttendanceByDateandCourse(Date start, Date end, int semId, int courseId);

   public List<AttendanceDashboardDTO> findAttendanceBySemester();
   
   public  List<StudentRollCallDTO> findAttendanceByStudentAndCourse(String startDate, String endDate, int studentId, String semId);



























   //   @Query("SELECT new com.ucsy.ams.dto.AttendanceByDateAllRecordDTO( a.id, acc.name, acc.email, a.date, a.isPresent, " +
//           "s.id, ts.dayOfWeek, ts.startTime, ts.endTime, c.code," +
//           " c.courseName, sem.semesterName, count(a.id) as totalAttendance, sum(case when a.isPresent = true then 1 else 0 end) as currentAttendance ) FROM Attendance a LEFT JOIN Students s on " +
//           "a.student.id = s.id LEFT JOIN Account acc on s.id = acc.id LEFT JOIN TimeSlot ts" +
//           " on a.timeslot.id = ts.id LEFT JOIN Courses c on ts.course.id = c.id LEFT JOIN Semester sem" +
//           " on ts.semester.id = sem.id where a.date between ?1 and ?2 AND sem.id = ?3 GROUP BY a.date, a.student.id")
//   public List<AttendanceByDateAllRecordDTO> findAttendanceByDateAndSemester(Date start, Date end, int semId);

   //   {
//      String sql = "SELECT attendance.id, name, email, date, is_present,  COUNT(attendance.id) AS TOTAL_ATTENDANCE, SUM(CASE WHEN attendance.is_present = \"1\" THEN 1 ELSE 0 END) AS current_attendance, students_id, day_of_week, start_time, end_time, code, course_name,semester_name FROM attendance\n" +
//              "LEFT JOIN students on attendance.students_id = students.id\n" +
//              "LEFT JOIN account on students.id = account.id\n" +
//              " LEFT JOIN timeslot on attendance.timeslot_id = timeslot.id\n" +
//              "  LEFT JOIN courses on timeslot.courses_id = courses.id\n" +
//              "  LEFT JOIN semester on timeslot.semester_id = semester.id\n" +
//              " where attendance.date between :startDate and :endDate AND semester.id = :semId GROUP BY attendance.date, attendance.students_id";
//
//      List<AttendanceByDateAllRecordDTO> results = jdbcTemplate.query(sql, (rs, rowNum) -> {
//         int id = rs.getInt("id");
//          String name = rs.getString(name);
//          String email;
//          java.util.Date date;
//          boolean isPresent;
//          int studentId;
//          String dayOfWeek;
//          String startTime;
//          String endTime;
//          String courseCode;
//          String courseName;
//          String semster;
//          int totalAttendance;
//          int currentAttendance;
//      })
//   }


}
