package com.ucsy.ams.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.security.Principal;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ucsy.ams.dto.GetAttendanceByDateAndCourseDTO;
import com.ucsy.ams.dto.GetTimeSlotBySemesterIdDTO;
import com.ucsy.ams.dto.StudentAccountDto;
import com.ucsy.ams.dto.TeacherAttendanceByCourseDTO;
import com.ucsy.ams.entity.Account;
import com.ucsy.ams.entity.Attendance;
import com.ucsy.ams.entity.Courses;
import com.ucsy.ams.entity.Students;
import com.ucsy.ams.entity.Teachers;
import com.ucsy.ams.entity.TimeSlot;
import com.ucsy.ams.repository.AccountRepo;
import com.ucsy.ams.repository.CourseRepo;
import com.ucsy.ams.repository.StudentRepo;
import com.ucsy.ams.repository.TeacherRepository;
import com.ucsy.ams.repository.TimeSlotRepo;
import com.ucsy.ams.service.AttendaceService;
import com.ucsy.ams.service.TeacherService;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

	@Autowired
	private AccountRepo accRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CourseRepo courseRepo;

	@Autowired
	private StudentRepo studentRepo;

	@Autowired
	private TeacherRepository teacherRepo;
	
	@Autowired
	private TimeSlotRepo timeSlotRepo;

	@Autowired
	private AttendaceService attendaceService;

	@ModelAttribute
	public void commonAcc(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			Account acc = accRepo.findByEmail(email);
			System.out.println(acc.getRole());
			m.addAttribute("user", acc);
			m.addAttribute("titles", "Teacher DashBoard");
			m.addAttribute("condition", true);
			m.addAttribute("check", false);

			m.addAttribute("title", "Teacher");
			List<Courses> course = courseRepo.findByCourseTeacherId(acc.getId());
			m.addAttribute("totalCourse", course.size());
			Optional<Teachers> teacher = teacherRepo.findById(acc.getId());
			m.addAttribute("fac", teacher.get().getFaculty());
			List<String> semester = courseRepo.findDistinctSemesterById(acc.getId());
			m.addAttribute("semesterTotal", semester.size());

			List<GetTimeSlotBySemesterIdDTO> timeslot = teacherService.getTimeSlotByTeacherId(acc.getId());
			Gson gson = new Gson();
			Type listType = new TypeToken<List<GetTimeSlotBySemesterIdDTO>>() {
			}.getType();
			String jsonArray = gson.toJson(timeslot, listType);

			m.addAttribute("jsonArray", jsonArray);

			LocalDate date = LocalDate.now();
			int year = date.getYear();
			int year1 = year - 1;
			m.addAttribute("dt", year);
			m.addAttribute("dt1", year1);
			m.addAttribute("pwd1", false);
			m.addAttribute("pwd2", true);
			m.addAttribute("pwd3", false);
			m.addAttribute("z1", true);
			m.addAttribute("z2", false);
		}
	}

	@GetMapping({ "/dash", "/dashboard" })
	public String dashboard(Model m) {
		return "teacher-student-dashboard";
	}

	@GetMapping("take-attendance")
	public String take_attendance(Model m) {
		Account acc = (Account) m.getAttribute("user");
		List<Courses> course = courseRepo.findByCourseTeacherId(acc.getId());
		m.addAttribute("course", course);
		return "teacher-course";
	}

	@GetMapping("take-std-attendance/{courseId}/{code}")
	public String take_attendance_student(@PathVariable("courseId") int courseId, Model m) {
		List<StudentAccountDto> st = studentRepo.getAllStudentListByCourseId(courseId);
		if (st.isEmpty()) {
			m.addAttribute("t", false);
		} else {
			m.addAttribute("t", true);
		}
		Account acc = (Account) m.getAttribute("user");

		LocalDate date = LocalDate.now();
		DayOfWeek dat = date.getDayOfWeek();
		String day = null;
		switch (dat) {
		case MONDAY:
			day = "1";
			break;
		case TUESDAY:
			day = "2";
			break;
		case WEDNESDAY:
			day = "3";
			break;
		case THURSDAY:
			day = "4";
			break;
		case FRIDAY:
			day = "5";
			break;
		default:
			break;
		}

		List<Integer> id = timeSlotRepo.getTimeSlotId(acc.getId(), day, courseId);
		if (id.isEmpty()) {
			m.addAttribute("courseId", 0);
			m.addAttribute("t", false);
		} else {
			m.addAttribute("courseId", courseId);
			m.addAttribute("t", true);
		}
		m.addAttribute("student", st);

		return "take-attendance";
	}

	@PostMapping("take-std-attendance")
	public String take_attendance_student(@RequestParam(name = "stdId") List<Integer> stdId,
			@RequestParam(name = "isPresent") List<Boolean> isPresent, @RequestParam("remark") List<String> remark,
			@RequestParam(name = "courseId") int courseId, Model m) {
		Account acc = (Account) m.getAttribute("user");

		LocalDate date = LocalDate.now();
		DayOfWeek dat = date.getDayOfWeek();
		Date sqlDate = Date.valueOf(date);
		String day = null;
		switch (dat) {
		case MONDAY:
			day = "1";
			break;
		case TUESDAY:
			day = "2";
			break;
		case WEDNESDAY:
			day = "3";
			break;
		case THURSDAY:
			day = "4";
			break;
		case FRIDAY:
			day = "5";
			break;
		default:
			break;
		}
		List<Integer> id = timeSlotRepo.getTimeSlotId(acc.getId(), day, courseId);
		if (id == null) {
			return "redirect:/teacher/take-attendance";
		}
		
		Optional<TimeSlot> timeSlot = timeSlotRepo.findById(id.get(0));
		List<Attendance> attent = new ArrayList<Attendance>();
		for (int i = 0; i < stdId.size(); i++) {
			Attendance att = new Attendance();
			Optional<Students> student = studentRepo.findById(stdId.get(i));
			att.setDate(sqlDate);
			att.setRemark(remark.get(i));
			att.setTimeslot(timeSlot.orElse(new TimeSlot()));
			att.setStudent(student.orElse(new Students()));
			att.setPresent(isPresent.get(i));

			attent.add(att);
		}
		attendaceService.attendanceSave(attent);
		return "redirect:/teacher/dash";
	}

	@Autowired
	private TeacherService teacherService;

	private List<GetAttendanceByDateAndCourseDTO> result;
	private Date startDate;
	private Date endDate;
	private String semester;

	@GetMapping("/attendance")
	public String showAttendance(Model m) {
		Account acc = (Account) m.getAttribute("user");
		List<Courses> course = courseRepo.findByCourseTeacherId(acc.getId());
		m.addAttribute("course", course);
		return "roll-call";
	}

	@GetMapping("/attendance/{courseId}")
	public String roll_call_student(@PathVariable("courseId") int courseId, Model m) {
		m.addAttribute("courseId", courseId);
		return "roll-call-view";
	}

	@PostMapping("/attendance/attend")
	public String getAttendanceByDateAndCourse(@ModelAttribute TeacherAttendanceByCourseDTO attendanceDto,
			BindingResult bindingResult, Model model) {
		Account acc = (Account) model.getAttribute("user");
		if (bindingResult.hasErrors()) {
			return "roll-call-view";
		}

		result = teacherService.getAttendanceByDateAndCourse(attendanceDto.getStartDate(), attendanceDto.getEndDate(),
				acc.getId(), attendanceDto.getCourseId());
		startDate = attendanceDto.getStartDate();
		endDate = attendanceDto.getEndDate();

		if (!result.isEmpty()) {
			semester = result.get(0).getCourseName();

			for (GetAttendanceByDateAndCourseDTO getAttendance : result) {
				float mark = (((float) getAttendance.getCurrentAttendance()
						/ (float) getAttendance.getTotalAttendance()) * 5);
				getAttendance.setMark(String.format("%.2f", mark));

			}
		}

		model.addAttribute("attendance", result);
		return "roll-call-mark";
	}

//	    download excel
	@GetMapping("/attendance/download")
	public ResponseEntity<Resource> downloadExcel() {
		String[] headersArray = { "ID", "Name", "Email", "Individual Attendance", "Total Attendance", "Mark",
				"Signautre" };

		// Generate Excel file
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Attendance");

//			// Start Set column widths
		sheet.setColumnWidth(0, 256 * 10); // ID column width
		sheet.setColumnWidth(1, 256 * 15); // Name column width
		sheet.setColumnWidth(2, 256 * 35); // Email column width
		sheet.setColumnWidth(3, 256 * 30); // Individual Attendance column width
		sheet.setColumnWidth(4, 256 * 30); // Total Attendance column width
		sheet.setColumnWidth(5, 256 * 30); // Attendance Percentage column width
		sheet.setColumnWidth(6, 256 * 30); // Assign column width
//			// End Set column widths

//			// Header Design at row 0 and 1
		Font boldFont = workbook.createFont();
		boldFont.setBold(true);
		boldFont.setFontHeightInPoints((short) 14);

		CellStyle boldCenteredStyle = workbook.createCellStyle();
		boldCenteredStyle.setFont(boldFont);
		boldCenteredStyle.setAlignment(HorizontalAlignment.CENTER);
		boldCenteredStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		Row titleRow = sheet.createRow(0);
		Cell titleCell = titleRow.createCell(0);
		titleCell.setCellValue("University of Computer Studies Yangon (UCSY)");
		titleCell.setCellStyle(boldCenteredStyle);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, headersArray.length - 1));
//			//--------------------------------

//			Semester at row 2
		Row semesterRow = sheet.createRow(2);
		Cell semesterCell = semesterRow.createCell(0);
		semesterCell.setCellValue(semester.toUpperCase());
		semesterCell.setCellStyle(boldCenteredStyle);
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, headersArray.length - 1));

//			Start Date to end date at row 3
		Row dateRow = sheet.createRow(3);
		Cell dateCell = dateRow.createCell(0);
		dateCell.setCellValue("\"" + startDate + "\" \"" + endDate + "\"");
		dateCell.setCellStyle(boldCenteredStyle);
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, headersArray.length - 1));

//			// Date Design Row 4
		Font dateFont = workbook.createFont();
		dateFont.setBold(true);
		dateFont.setFontHeightInPoints((short) 10);

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setFont(dateFont);
		dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formatterDateTime = now.format(formatter);

		Row dateTimeRow = sheet.createRow(4);
		Cell dateTimeCell = dateTimeRow.createCell(0);

		dateTimeRow.setHeightInPoints(20);
		dateTimeCell.setCellValue("Date : " + formatterDateTime);
		dateTimeCell.setCellStyle(dateCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, headersArray.length - 1));
//			//--------------------------------

//			//  column header at row 5
		Row headerRow = sheet.createRow(5);
		for (int i = 0; i < headersArray.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headersArray[i]);
			cell.setCellStyle(boldCenteredStyle);
		}
//			//--------------------------------

//			// Fill data rows at row 6

		CellStyle dataCellStyle = workbook.createCellStyle();
		dataCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		int rowNum = 6;
		for (GetAttendanceByDateAndCourseDTO data : result) {
			Row row = sheet.createRow(rowNum++);
			row.setHeightInPoints(30);

			Cell cell1 = row.createCell(0);
			Cell cell2 = row.createCell(1);
			Cell cell3 = row.createCell(2);
			Cell cell4 = row.createCell(3);
			Cell cell5 = row.createCell(4);
			Cell cell6 = row.createCell(5);

			cell1.setCellValue(data.getId());
			cell1.setCellStyle(dataCellStyle);

			cell2.setCellValue(data.getName());
			cell2.setCellStyle(dataCellStyle);

			cell3.setCellValue(data.getEmail());
			cell3.setCellStyle(dataCellStyle);

			cell4.setCellValue(data.getCurrentAttendance());
			cell4.setCellStyle(dataCellStyle);

			cell5.setCellValue(data.getTotalAttendance());
			cell5.setCellStyle(dataCellStyle);

			cell6.setCellValue(data.getMark());
			cell6.setCellStyle(dataCellStyle);
		}
//			//--------------------------------

//			// Prepare Excel file for download
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			workbook.write(outputStream);
			workbook.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		// Return Excel file for download
		byte[] excelBytes = outputStream.toByteArray();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=" + startDate + "_to_" + endDate + "_attendance.xlsx");

		return ResponseEntity.ok().headers(headers)
				.contentType(
						MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
				.body(new ByteArrayResource(excelBytes));
	}
	
	// change Password password
			@GetMapping("/change-password")
			public String changePassword(Model m) {
				return "change-password";
			}

			@PostMapping("/change-password")
			public String changePassword(@RequestParam("currentPassword") String currentPassword,
					@RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword,
					Principal principal, Model model) {
				String email = principal.getName();
				Account acc = accRepo.findByEmail(email);

				if (!passwordEncoder.matches(currentPassword, acc.getPassword())) {
					model.addAttribute("error", "Current password is incorrect.");
					return "change-password";
				}

				if (!newPassword.equals(confirmPassword)) {
					model.addAttribute("error", "New passwords do not match.");
					return "change-password";
				}

				acc.setPassword(passwordEncoder.encode(newPassword));
				accRepo.save(acc);
				model.addAttribute("message", "Password changed successfully.");
				return "redirect:/teacher/dash";
			}
}
