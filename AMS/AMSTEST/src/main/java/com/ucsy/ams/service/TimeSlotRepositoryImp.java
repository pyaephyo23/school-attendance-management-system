package com.ucsy.ams.service;

import com.ucsy.ams.dto.GetTimeSlotBySemesterIdDTO;
import com.ucsy.ams.repository.TimeSlotRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TimeSlotRepositoryImp implements TimeSlotRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("deprecation")
	@Override
    public List<GetTimeSlotBySemesterIdDTO> findTimeSlotByTeacherIdd(int studentId) {
    	
        String sql = " SELECT day_of_week, start_time, end_time, code as course_name, teaches_id FROM timeslot " +
                "    LEFT JOIN semester on timeslot.semester_id = semester.id " +
                "    LEFT JOIN courses on timeslot.courses_id = courses.id " +
                "    LEFT JOIN teacher_has_course on courses.id = teacher_has_course.courses_id " +
                "WHERE teacher_has_course.teaches_id= ? ORDER BY timeslot.day_of_week ASC, timeslot.start_time ASC";

        return jdbcTemplate.query(sql, new Object[]{studentId}, new TimeSlotRepositoryImp.TimeSlotRowMapper());
    }

    private static class TimeSlotRowMapper implements RowMapper<GetTimeSlotBySemesterIdDTO> {
        @Override
        public GetTimeSlotBySemesterIdDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

            int day_of_week = rs.getInt("day_of_week");
            String start_time = rs.getString("start_time");
            String end_time = rs.getString("end_time");
            String course_name = rs.getString("course_name");
            int teaches_id = rs.getInt("teaches_id");

            return new GetTimeSlotBySemesterIdDTO(day_of_week, start_time, end_time, course_name, teaches_id);
        }
    }

    @SuppressWarnings("deprecation")
	@Override
    public List<GetTimeSlotBySemesterIdDTO> findTimeSlotBySemesterId(String semesterName, int studentId) {
        String sql = "SELECT day_of_week, start_time, end_time,code as course_name, teaches_id,account.name FROM timeslot " +
                "LEFT JOIN semester on timeslot.semester_id = semester.id " +
                "LEFT JOIN courses on timeslot.courses_id = courses.id " +
                "LEFT JOIN takes on courses.id = takes.couses_id "+
                "LEFT JOIN teacher_has_course on courses.id = teacher_has_course.courses_id " +
                "LEFT JOIN teachers on teacher_has_course.teaches_id = teachers.id  " +
                "LEFT JOIN account on teachers.id = account.id " +
                "WHERE semester.semester_name = ? AND takes.students_id = ? ORDER BY timeslot.day_of_week ASC, timeslot.start_time ASC;";

        return jdbcTemplate.query(sql, new Object[]{semesterName, studentId}, new TimeSlotRepositoryImp.TimeSlotStudentRowMapper());
    }

    private static class TimeSlotStudentRowMapper implements RowMapper<GetTimeSlotBySemesterIdDTO> {
        @Override
        public GetTimeSlotBySemesterIdDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

            int day_of_week = rs.getInt("day_of_week");
            String start_time = rs.getString("start_time");
            String end_time = rs.getString("end_time");
            String course_name = rs.getString("course_name");
            int teaches_id = rs.getInt("teaches_id");
            String teacher_name = rs.getString("name");

            return new GetTimeSlotBySemesterIdDTO(day_of_week, start_time, end_time, course_name, teaches_id,  teacher_name);
        }
    }
}
