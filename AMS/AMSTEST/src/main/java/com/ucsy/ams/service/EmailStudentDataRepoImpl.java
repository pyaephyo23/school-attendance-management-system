package com.ucsy.ams.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ucsy.ams.dto.EmailAttendanceDto;
import com.ucsy.ams.repository.EmailStudentDataRepo;

@Repository
public class EmailStudentDataRepoImpl implements EmailStudentDataRepo{

	@Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("deprecation")
	@Override
    public List<EmailAttendanceDto> findStudentAttendanceData() {
    	LocalDate date = LocalDate.now();
		int month = date.getMonthValue();
		int year = date.getYear();
		String months = String.valueOf(month-1);
		String years = String.valueOf(year);
		
		String startDate = years+"-"+months+"-1";
		String endDate = years+"-"+month+"-31";
    	
        String sql = "SELECT s.id, a.name, a.email, s.roll_no AS rollNo, COUNT(atd.id) AS total_classes, " +
                     "SUM(CASE WHEN atd.is_present = b'1' THEN 1 ELSE 0 END) AS attended_classes " +
                     "FROM students s " +
                     "JOIN account a ON s.id = a.id " +
                     "JOIN attendance atd ON s.id = atd.students_id " +
                     "WHERE atd.date BETWEEN ? and ?"
                     + " GROUP BY s.id, a.name, a.email, s.roll_no";

        List<EmailAttendanceDto> results = jdbcTemplate.query(sql, new Object[]{ startDate,endDate}, (rs, rowNum) -> {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String rollNo = rs.getString("rollNo");
            long totalClasses = rs.getLong("total_classes");
            long attendedClasses = rs.getLong("attended_classes");
            return new EmailAttendanceDto(id, name, email, rollNo, totalClasses, attendedClasses);
        });

        return results;
    }
}
