package com.ucsy.ams.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ucsy.ams.dto.AttendanceByDateAllRecordDTO;
import com.ucsy.ams.dto.AttendanceByDateDTO;
import com.ucsy.ams.dto.AttendanceDashboardDTO;
import com.ucsy.ams.dto.CourseSemesterTimeSlotDto;
import com.ucsy.ams.dto.StudentAccountDto;
import com.ucsy.ams.dto.TeacherAccountDto;
import com.ucsy.ams.entity.Account;
import com.ucsy.ams.entity.Courses;
import com.ucsy.ams.entity.Semester;
import com.ucsy.ams.entity.Students;
import com.ucsy.ams.entity.Takes;
import com.ucsy.ams.entity.TeacherHasCourse;
import com.ucsy.ams.entity.Teachers;
import com.ucsy.ams.entity.TimeSlot;
import com.ucsy.ams.repository.AccountRepo;
import com.ucsy.ams.repository.CourseRepo;
import com.ucsy.ams.repository.SemesterRepo;
import com.ucsy.ams.repository.StudentRepo;
import com.ucsy.ams.repository.TakesRepo;
import com.ucsy.ams.repository.TeacherHasCourseRepo;
import com.ucsy.ams.repository.TimeSlotRepo;
import com.ucsy.ams.service.AccountService;
import com.ucsy.ams.service.AdminService;
import com.ucsy.ams.service.ClassRoasterService;
import com.ucsy.ams.service.CouresCreateService;
import com.ucsy.ams.service.StudentAccountService;
import com.ucsy.ams.service.TeacherAccountService;

@Controller
@RequestMapping("/classRoster")
public class ClassRoasterController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private CourseRepo courseRepo;

	@Autowired
	private AccountRepo userRepo;

	@Autowired
	private StudentRepo studentRepo;

	@Autowired
	private ClassRoasterService classRoasterService;

	private List<AttendanceByDateAllRecordDTO> result;
	private Date startDate;
	private Date endDate;
	private String semester;

	@Autowired
	private AccountService accService;

	@Autowired
	private TeacherAccountService service;

	@Autowired
	private CouresCreateService couresCreateService;

	@Autowired
	private SemesterRepo semesterRepo;

	@Autowired
	private StudentAccountService stdService;

	@Autowired
	private TimeSlotRepo timeSlotRepo;

	@Autowired
	private TeacherHasCourseRepo teacherHasCourseRepo;

	@ModelAttribute
	public void commonUser(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			Account user = userRepo.findByEmail(email);
			m.addAttribute("user", user);
			m.addAttribute("titles", "ClassRoaster DashBoard");
			m.addAttribute("condition", false);
			m.addAttribute("check", true);
			Account acc = (Account) m.getAttribute("user");
			TeacherAccountDto teacher = service.getEachTeacherById(acc.getId());
			m.addAttribute("semName", teacher.getSemester());
			LocalDate date = LocalDate.now();
			int year = date.getYear();
			int year1 = year-1;
			m.addAttribute("dt", year);
			m.addAttribute("dt1", year1);
		}

	}

	@GetMapping({ "/dash", "/dashboard" })
	public String profile(Model model) {
		float totalAttendance = 0.00F;
		float totalCurrentAttendance = 0.00F;
		float totalAttendancePercentage = 0.00F;

//		result from database
		// List<Account> totalStudents = accService.findTotalByRole("ROLE_STUDENT");
		Account acc = (Account) model.getAttribute("user");
		TeacherAccountDto teacher = service.getEachTeacherById(acc.getId());
		List<StudentAccountDto> totalStudents = stdService.getStudentListBySemester("ROLE_STUDENT",
				teacher.getSemester());
		List<Account> totalTeachers = accService.findTotalByRole("ROLE_TEACHER");
		List<AttendanceDashboardDTO> result = adminService.getAttendanceBySemester();

		Gson gson = new Gson();
		Type listType = new TypeToken<List<AttendanceDashboardDTO>>() {
		}.getType();
		String jsonArray = gson.toJson(result, listType);

//		total calculate all semester
		for (AttendanceDashboardDTO each : result) {
			totalAttendance += each.getTotalAttendance();
			totalCurrentAttendance += each.getCurrentAttendance();
		}
		
		if(totalCurrentAttendance != 0 && totalAttendance !=0) {
			totalAttendancePercentage += (totalCurrentAttendance / totalAttendance) * 100;	
		}
		

		model.addAttribute("totalStudents", totalStudents.size());
		model.addAttribute("totalTeachers", totalTeachers.size());
		model.addAttribute("totalAttendancePercentage", String.format("%.2f", totalAttendancePercentage));
		model.addAttribute("jsonArray", jsonArray);
		// write code to show dash board data
		return "dashboard";
	}

	@GetMapping("/teacher-list")
	public String teacher_list(Model m) {
		// write code to show teacher list;
		m.addAttribute("tit", "Teacher");
		List<TeacherAccountDto> all = service.getAllTeacher("ROLE_TEACHER");
		m.addAttribute("roaster", all);
		m.addAttribute("con", false);
		m.addAttribute("sign", true);

		return "show-all-list";
	}

	@GetMapping("/student-list")
	public String student_list(Model m) {
		// write code to show student list;
		Account acc = (Account) m.getAttribute("user");
		TeacherAccountDto teacher = service.getEachTeacherById(acc.getId());
		List<StudentAccountDto> all = stdService.getStudentListBySemester("ROLE_STUDENT", teacher.getSemester());
		m.addAttribute("student", all);
		m.addAttribute("test", all.isEmpty());
		m.addAttribute("con", false);
		m.addAttribute("sign", true);

		return "student-list";
	}

	@GetMapping("/course-form")
	public String create_course(Model m) {
		m.addAttribute("courses", new Courses());
		return "course-form";
	}

	@PostMapping("/course-form")
	public String createCourse(@ModelAttribute("courses") Courses course) {
		courseRepo.save(course);
		return "redirect:/classRoster/course-view";
	}

//	@GetMapping("/sem-form")
//	public String semesterCreate(Model m) {
//		return "sem-form";
//	}
//
//	@PostMapping("/sem-form")
//	public String semesterCreate(@ModelAttribute("sem") Semester sem) {
//		semesterRepo.save(sem);
//		return "sem-view";
//	}

	@GetMapping("/time-slot-form")
	public String time_slot_Create(Model m) {
		m.addAttribute("timeSlot", new TimeSlot());
		Account acc = (Account) m.getAttribute("user");
		TeacherAccountDto teacher = service.getEachTeacherById(acc.getId());
		Semester sem = semesterRepo.findBySemesterName(teacher.getSemester());

		m.addAttribute("sem", sem);
		m.addAttribute("course", courseRepo.findAll());
		return "time-slot-form";
	}

	@PostMapping("/time-slot-form")
	public String time_slot_Create(@ModelAttribute("timeSlot") TimeSlot timeSlot) {
		timeSlotRepo.save(timeSlot);
		return "redirect:/classRoster/time-slot-view";
	}

	// Attendance View
	@GetMapping("/attendance")
	public String showAttendance(Model m) {
		Account acc = (Account) m.getAttribute("user");
		TeacherAccountDto teacher = service.getEachTeacherById(acc.getId());
		Semester sem = semesterRepo.findBySemesterName(teacher.getSemester());
		m.addAttribute("sem", sem);
		return "AttendanceView";
	}

	@PostMapping("/attendance")
	public String getAttendanceByDateAndSemester(@ModelAttribute("attendance") AttendanceByDateDTO dto, Model model) {
		Account acc = (Account) model.getAttribute("user");
		TeacherAccountDto teacher = service.getEachTeacherById(acc.getId());
		Semester sem = semesterRepo.findBySemesterName(teacher.getSemester());
		result = classRoasterService.getAttendanceByDate(dto.getStartDate(), dto.getEndDate(), sem.getId());
		System.out.println(dto.getSemId()+"----------------");
		startDate = dto.getStartDate();
		endDate = dto.getEndDate();

		if (!result.isEmpty()) {
			semester = result.get(0).getSemster();

			for (AttendanceByDateAllRecordDTO ls : result) {
				float percentage = ((float) ls.getCurrentAttendance() / (float) ls.getTotalAttendance()) * 100;
				ls.setPercentage(String.format("%.2f", percentage));
			}
		}

		model.addAttribute("attendance", result);
		return "attendance-download";
	}

	@GetMapping("/download")
	public ResponseEntity<Resource> downloadExcel() {
		String[] headersArray = { "ID", "Name", "Email", "Individual Attendance", "Total Attendance",
				"Attendance Percentage" };

		// Generate Excel file
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Attendance");

//		// Start Set column widths
		sheet.setColumnWidth(0, 256 * 10); // ID column width
		sheet.setColumnWidth(1, 256 * 15); // Name column width
		sheet.setColumnWidth(2, 256 * 35); // Email column width
		sheet.setColumnWidth(3, 256 * 30); // Individual Attendance column width
		sheet.setColumnWidth(4, 256 * 30); // Total Attendance column width
		sheet.setColumnWidth(5, 256 * 30); // Attendance Percentage column width
//		// End Set column widths

//		// Header Design at row 0 and 1
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
//		//--------------------------------

//		Semester at row 2
		Row semesterRow = sheet.createRow(2);
		Cell semesterCell = semesterRow.createCell(0);
		semesterCell.setCellValue(semester.toUpperCase());
		semesterCell.setCellStyle(boldCenteredStyle);
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, headersArray.length - 1));

//		Start Date to end date at row 3
		Row dateRow = sheet.createRow(3);
		Cell dateCell = dateRow.createCell(0);
		dateCell.setCellValue("\"" + startDate + "\" \"" + endDate + "\"");
		dateCell.setCellStyle(boldCenteredStyle);
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, headersArray.length - 1));

//		// Date Design Row 4
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
//		//--------------------------------

//		//  column header at row 5
		Row headerRow = sheet.createRow(5);
		for (int i = 0; i < headersArray.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headersArray[i]);
			cell.setCellStyle(boldCenteredStyle);
		}
//		//--------------------------------

//		// Fill data rows at row 6
		int rowNum = 6;
		for (AttendanceByDateAllRecordDTO data : result) {
			Row row = sheet.createRow(rowNum++);
			Cell cell1 = row.createCell(0);
			Cell cell2 = row.createCell(1);
			Cell cell3 = row.createCell(2);
			Cell cell4 = row.createCell(3);
			Cell cell5 = row.createCell(4);
			Cell cell6 = row.createCell(5);

			CellStyle redBgStyle = workbook.createCellStyle();
			redBgStyle.setAlignment(HorizontalAlignment.RIGHT);

			// if row call percentage is lower 75, show background red
			if (Float.parseFloat(data.getPercentage()) < 75.00) {
				Font whiteFont = workbook.createFont();
				whiteFont.setColor(IndexedColors.WHITE.getIndex());

				redBgStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
				redBgStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				redBgStyle.setFont(whiteFont);

				cell1.setCellValue(data.getId());
				cell1.setCellStyle(redBgStyle);
				cell2.setCellValue(data.getName());
				cell2.setCellStyle(redBgStyle);
				cell3.setCellValue(data.getEmail());
				cell3.setCellStyle(redBgStyle);
				cell4.setCellValue(data.getCurrentAttendance());
				cell4.setCellStyle(redBgStyle);
				cell5.setCellValue(data.getTotalAttendance());
				cell5.setCellStyle(redBgStyle);
				cell6.setCellValue(data.getPercentage() + " %");
				cell6.setCellStyle(redBgStyle);
			} else {
				cell1.setCellValue(data.getId());
				cell2.setCellValue(data.getName());
				cell3.setCellValue(data.getEmail());
				cell4.setCellValue(data.getCurrentAttendance());
				cell5.setCellValue(data.getTotalAttendance());
				cell6.setCellValue(data.getPercentage() + " %");
			}

		}
//		//--------------------------------

//		// Prepare Excel file for download
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
//
//	// Semester View
//	@GetMapping("/sem-view")
//	public String semester_view(Model m) {
//		m.addAttribute("sem", semesterService.getAllSemesters());
//		return "sem-view";
//	}

	// Course View
	@GetMapping("/course-view")
	public String course_view(Model m) {
		m.addAttribute("course", courseRepo.findAll());
		return "course-view";
	}

	// TimeSlot view
	@GetMapping("/time-slot-view")
	public String timeslot_view(Model m) {
		m.addAttribute("course", timeSlotRepo.getAllCourseDetail());
		return "time-slot-view";
	}

//	// Edit Semester
//	@GetMapping("/sem-edit/{id}")
//	public String edit_Semester(@PathVariable(value = "id") int id, Model m) {
//		Semester sem = semesterRepo.findById(id);
//		m.addAttribute("list", sem);
//		return "sem-edit";
//	}
//
//	@PostMapping("/sem-edit")
//	public String upate_Semester(@ModelAttribute("list") Semester list, Model m) {
//		semesterRepo.updateSem(list.getStartDate(), list.getEndDate(), list.getId());
//		return "redirect:/classRoster/sem-view";
//	}

	// Edit Course
	@GetMapping("/course-edit/{code}")
	public String edit_Course(@PathVariable(value = "code") String code, Model m) {
		Courses course = courseRepo.findByCode(code);
		m.addAttribute("list", course);
		return "course-edit";
	}

	@PostMapping("/course-edit")
	public String upate_Course(@ModelAttribute("list") Courses course, Model m) {
		couresCreateService.updateCourse(course);
		return "redirect:/classRoster/course-view";
	}

	// Edit Time Slot
	@GetMapping("/time-slot-edit/{id}")
	public String edit_TimeSlot(@PathVariable(value = "id") int id, Model m) {
		CourseSemesterTimeSlotDto list = timeSlotRepo.getEachCourseDetail(id);
		m.addAttribute("list", list);
		return "time-slot-edit";
	}

	@PostMapping("/time-slot-edit")
	public String upate_TimeSlot(@ModelAttribute("list") CourseSemesterTimeSlotDto time, Model m) {
		timeSlotRepo.timeSlotUpdate(time.getStartTime(), time.getEndTime(), time.getDayOfWeek(), time.getId());
		return "redirect:/classRoster/time-slot-view";
	}

	// Sign Teacher
	@GetMapping("/sign/{id}")
	public String sign_teacher(@PathVariable(value = "id") int id, Model m) {
		Optional<Account> teacher = userRepo.findById(id);
		m.addAttribute("name", teacher.orElse(new Account()).getName());
		m.addAttribute("teacherId", id);
		m.addAttribute("course", courseRepo.findAll());
		List<CourseSemesterTimeSlotDto> cou = timeSlotRepo.getCourseForEachTeacherDetail(id);
		m.addAttribute("courses", cou);
		return "sign-teacher";
	}

	@PostMapping("/sign")
	public String sign_teacher(@ModelAttribute("course") Courses course, @RequestParam("teacherId") int id, Model m) {
		Optional<Teachers> teacher = service.findById(id);
		TeacherHasCourse sign = new TeacherHasCourse();
		sign.setTeacher(teacher.get());
		sign.setCourse(course);
		teacherHasCourseRepo.save(sign);
		return "redirect:/classRoster/sign/" + id;
	}

	// Sign Student
	@GetMapping("/sign-student/{id}/{semesterName}")
	public String sign_Student(@PathVariable(value = "id") int id, @PathVariable("semesterName") String semesterName,
			Model m) {
		Optional<Account> student = userRepo.findById(id);
		m.addAttribute("name", student.orElse(new Account()).getName());
		m.addAttribute("studentId", id);
		Optional<Students> std = studentRepo.findById(id);

		List<Courses> cou = courseRepo.findBySemester(std.get().getSemester());
		m.addAttribute("courses", cou);
		List<CourseSemesterTimeSlotDto> take = timeSlotRepo.getCourseForEachStudentDetail(id);
		m.addAttribute("take", take);
		return "sign-student";
	}

	@Autowired
	private TakesRepo takesRepo;
	
	@PostMapping("/sign-student")
	public String sign_Student(@ModelAttribute("courses") Courses course, @RequestParam("studentId") int id, Model m) {
		Takes sign = new Takes();
		Optional<Students> std = studentRepo.findById(id);
		sign.setStudent(std.get());
		sign.setCourse(course);
		takesRepo.save(sign);
		return "redirect:/classRoster/sign-student/" + id +"/"+ std.get().getSemester();
	}

}
