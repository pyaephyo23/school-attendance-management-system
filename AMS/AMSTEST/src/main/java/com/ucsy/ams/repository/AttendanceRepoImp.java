package com.ucsy.ams.repository;

import com.ucsy.ams.dto.AttendanceByDateAllRecordDTO;
import com.ucsy.ams.dto.AttendanceDashboardDTO;
import com.ucsy.ams.dto.GetAttendanceByDateAndCourseDTO;
import com.ucsy.ams.dto.StudentRollCallDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class AttendanceRepoImp implements AttendanceRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings("deprecation")
	@Override
	public List<AttendanceByDateAllRecordDTO> findAttendanceByDateAndSemester(Date start, Date end, int semId) {
		String sql = "SELECT attendance.id, account.name, account.email, attendance.date, attendance.is_present, "
				+ "COUNT(attendance.id) AS TOTAL_ATTENDANCE, "
				+ "SUM(CASE WHEN attendance.is_present = 1 THEN 1 ELSE 0 END) AS current_attendance, "
				+ "students.id AS students_id, timeslot.day_of_week, timeslot.start_time, timeslot.end_time, "
				+ "courses.code, courses.course_name, semester.semester_name " + "FROM attendance "
				+ "LEFT JOIN students ON attendance.students_id = students.id "
				+ "LEFT JOIN account ON students.id = account.id "
				+ "LEFT JOIN timeslot ON attendance.timeslot_id = timeslot.id "
				+ "LEFT JOIN courses ON timeslot.courses_id = courses.id "
				+ "LEFT JOIN semester ON timeslot.semester_id = semester.id "
				+ "WHERE attendance.date BETWEEN ? AND ? AND semester.id = ? " + "GROUP BY attendance.students_id";

		return jdbcTemplate.query(sql, new Object[] { start, end, semId }, new AttendanceRowMapper());
	}

	private static class AttendanceRowMapper implements RowMapper<AttendanceByDateAllRecordDTO> {
		@Override
		public AttendanceByDateAllRecordDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			String email = rs.getString("email");
			Date date = rs.getDate("date");
			boolean isPresent = rs.getInt("is_present") == 1;
			int totalAttendance = rs.getInt("TOTAL_ATTENDANCE");
			int currentAttendance = rs.getInt("current_attendance");
			int studentId = rs.getInt("students_id");
			String dayOfWeek = rs.getString("day_of_week");
			String startTime = rs.getString("start_time");
			String endTime = rs.getString("end_time");
			String courseCode = rs.getString("code");
			String courseName = rs.getString("course_name");
			String semesterName = rs.getString("semester_name");

			return new AttendanceByDateAllRecordDTO(id, name, email, date, isPresent, studentId, dayOfWeek, startTime,
					endTime, courseCode, courseName, semesterName, totalAttendance, currentAttendance);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<GetAttendanceByDateAndCourseDTO> findAttendanceByDateandCourse(Date start, Date end, int teacherId,
			int courseId) {
		String sql = " SELECT attendance.id, name, email, date, COUNT(attendance.id) AS total_attendance, "
				+ "SUM(CASE WHEN attendance.is_present = \"1\" THEN 1 ELSE 0 END) AS current_attendance, "
				+ "students_id, code, course_name, semester_name FROM attendance "
				+ "LEFT JOIN students on attendance.students_id = students.id "
				+ "LEFT JOIN account on students.id = account.id "
				+ " LEFT JOIN timeslot on attendance.timeslot_id = timeslot.id "
				+ "LEFT JOIN courses on timeslot.courses_id = courses.id "
				+ "LEFT JOIN teacher_has_course th on th.courses_id = courses.id "
				+ "LEFT JOIN teachers t on th.teaches_id = t.id "
				+ "  LEFT JOIN semester on timeslot.semester_id = semester.id "
				+ " where attendance.date between ? and ? AND t.id = ? AND  timeslot.courses_id= ? GROUP BY attendance.students_id";

		return jdbcTemplate.query(sql, new Object[] { start, end, teacherId, courseId },
				new TeacherAttendanceRowMapper());
	}

	private static class TeacherAttendanceRowMapper implements RowMapper<GetAttendanceByDateAndCourseDTO> {
		@Override
		public GetAttendanceByDateAndCourseDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			String email = rs.getString("email");
			Date date = rs.getDate("date");
			int totalAttendance = rs.getInt("total_attendance");
			int currentAttendance = rs.getInt("current_attendance");
			int studentId = rs.getInt("students_id");
			String code = rs.getString("code");
			String courseName = rs.getString("course_name");
			String semesterName = rs.getString("semester_name");

			return new GetAttendanceByDateAndCourseDTO(id, name, email, date, totalAttendance, currentAttendance,
					studentId, code, courseName, semesterName);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<AttendanceDashboardDTO> findAttendanceBySemester() {
		LocalDate date = LocalDate.now();
		int month = date.getMonthValue();
		int year = date.getYear();
		String months = String.valueOf(month - 1);
		String years = String.valueOf(year);

		String startDate = years + "-" + months + "-1";
		String endDate = years + "-" + month + "-31";

		String sql = "SELECT COUNT(attendance.id) AS total_attendance, SUM(CASE WHEN attendance.is_present = \"1\" THEN 1 ELSE 0 END) AS current_attendance, semester_name FROM attendance "
				+ "LEFT JOIN students on attendance.students_id = students.id "
				+ "LEFT JOIN account on students.id = account.id "
				+ " LEFT JOIN timeslot on attendance.timeslot_id = timeslot.id "
				+ "  LEFT JOIN courses on timeslot.courses_id = courses.id "
				+ "  LEFT JOIN semester on timeslot.semester_id = semester.id " + " where attendance.date between "
				+ "? and ? GROUP BY timeslot.semester_id;";

		return jdbcTemplate.query(sql, new Object[] { startDate, endDate }, new DashboardAttendanceRowMapper());
	}

	private static class DashboardAttendanceRowMapper implements RowMapper<AttendanceDashboardDTO> {
		@Override
		public AttendanceDashboardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			int totalAttendance = rs.getInt("total_attendance");
			int currentAttendance = rs.getInt("current_attendance");
			String semesterName = rs.getString("semester_name");

			return new AttendanceDashboardDTO(totalAttendance, currentAttendance, semesterName);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<StudentRollCallDTO> findAttendanceByStudentAndCourse(String start, String end, int studentId, String semId) {
		String sql = " SELECT name, COUNT(attendance.id) AS total_attendance, "
				+ "SUM(CASE WHEN attendance.is_present = '1' THEN 1 ELSE 0 END) AS current_attendance, "
				+ "code, courses.course_name, semester_name " + "FROM attendance "
				+ "LEFT JOIN students on attendance.students_id = students.id "
				+ "LEFT JOIN account on students.id = account.id "
				+ "LEFT JOIN timeslot on attendance.timeslot_id = timeslot.id "
				+ "LEFT JOIN semester on timeslot.semester_id = semester.id "
				+ "LEFT JOIN courses on timeslot.courses_id = courses.id "
				+ "LEFT JOIN takes on courses.id = takes.couses_id " + "WHERE attendance.date BETWEEN ? AND ? "
				+ "AND takes.students_id = ? " + "AND semester.semester_name = ? " + "GROUP BY course_name";

		return jdbcTemplate.query(sql, new Object[] { start, end, studentId, semId }, new StudentAttendanceRowMapper());
	}

	private static class StudentAttendanceRowMapper implements RowMapper<StudentRollCallDTO> {
		@Override
		public StudentRollCallDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			String name = rs.getString("name");
			int totalAttendance = rs.getInt("total_attendance");
			int currentAttendance = rs.getInt("current_attendance");
			String code = rs.getString("code");
			String courseName = rs.getString("course_name");
			String semester = rs.getString("semester_name");

			return new StudentRollCallDTO(name, totalAttendance, currentAttendance, code, courseName, semester);
		}
	}
}
