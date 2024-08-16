package com.ucsy.ams.controller;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ucsy.ams.dto.AttendanceDashboardDTO;
import com.ucsy.ams.dto.StudentAccountDto;
import com.ucsy.ams.dto.TeacherAccountDto;
import com.ucsy.ams.entity.Account;
import com.ucsy.ams.entity.Semester;
import com.ucsy.ams.repository.AccountRepo;
import com.ucsy.ams.repository.SemesterRepo;
import com.ucsy.ams.service.AccountService;
import com.ucsy.ams.service.AdminService;
import com.ucsy.ams.service.SemesterService;
import com.ucsy.ams.service.StudentAccountService;
import com.ucsy.ams.service.TeacherAccountService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private AccountRepo accRepo;

	@Autowired
	private AccountService accService;

	@Autowired
	private TeacherAccountService service;

	@Autowired
	private StudentAccountService stdService;

	@Autowired
	private SemesterRepo semesterRepo;
	
	@Autowired
	private SemesterService semesterService;

	@ModelAttribute
	public void commonAcc(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			Account acc = accRepo.findByEmail(email);
			System.out.println(acc.getRole());
			m.addAttribute("user", acc);
			m.addAttribute("titles", "ADMIN DashBoard");
			m.addAttribute("condition", true);
			m.addAttribute("check", false);
			LocalDate date = LocalDate.now();
			int year = date.getYear();
			int year1 = year-1;
			m.addAttribute("dt", year);
			m.addAttribute("dt1", year1);
		}
	}

	@GetMapping({ "/dash", "/dashboard" })
	public String dashboard(Model model) {
		float totalAttendance = 0.00F;
		float totalCurrentAttendance = 0.00F;
		float totalAttendancePercentage = 0.00F;

//		result from database
		List<Account> totalStudents = accService.findTotalByRole("ROLE_STUDENT");
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
		totalAttendancePercentage += (totalCurrentAttendance / totalAttendance) * 100;

		model.addAttribute("totalStudents", totalStudents.size());
		model.addAttribute("totalTeachers", totalTeachers.size());
		model.addAttribute("totalAttendancePercentage", String.format("%.2f", totalAttendancePercentage));
		model.addAttribute("jsonArray", jsonArray);
		// write code to show dash board data
		return "dashboard";
	}

	@GetMapping("/class-roster")
	public String class_roaster(Model m) {
		// write code to show class roaster data
		m.addAttribute("con", true);
		m.addAttribute("sign", false);
		m.addAttribute("tit", "Roaster");
		List<TeacherAccountDto> all = service.getAllTeacher("ROLE_CLASSROASTER");
		m.addAttribute("roaster", all);
		return "roaster-list";
	}

	@GetMapping("/teacher-list")
	public String teacher_list(Model m) {
		// write code to show teacher list;
		m.addAttribute("con", true);
		m.addAttribute("sign", false);
		m.addAttribute("tit", "Teacher");
		List<TeacherAccountDto> all = service.getAllTeacher("ROLE_TEACHER");
		m.addAttribute("roaster", all);
		return "show-all-list";
	}

	@GetMapping("/student-list")
	public String student_list(Model m) {
		// write code to show student list;
		m.addAttribute("con", true);
		m.addAttribute("sign", false);
		List<StudentAccountDto> all = stdService.getStudentList("ROLE_STUDENT");
		m.addAttribute("student", all);
		m.addAttribute("test", all.isEmpty());
		return "student-list";
	}

	@GetMapping("/teacher-register")
	public String teacher_registration(Model m) {
		// write teacher form ;
		return "teacher-regis";
	}

	@PostMapping("/teacher-register")
	public String teacher_registration(@ModelAttribute("teacher") TeacherAccountDto dto) {
		// write teacher form ;

		accService.insertAccountTeachers(dto);
		return "redirect:/admin/teacher-list";
	}

//
//	@GetMapping("/student-register")
//	public String student_registration() {
//		// write teacher form ;
//		return "student-regis";
//	}
//	
	@GetMapping("/edit-list/{id}")
	public String edit_Teacher(@PathVariable(value = "id") int id, Model m) {
		TeacherAccountDto teacher = service.getEachTeacherById(id);
		List<Semester> sem = semesterRepo.findAll();
		m.addAttribute("list", teacher);
		System.out.println(sem.get(0).getSemesterName());
		System.out.println("================================");
		m.addAttribute("sem", sem);
		return "edit-list";
	}

	@PostMapping("/edit-list")
	public String upate_list(@ModelAttribute("list") TeacherAccountDto list, Model m) {

		accService.updateData(list);
		return "redirect:/admin/teacher-list";
	}

	@GetMapping("/roaster-list/{id}")
	public String edit_roaster(@PathVariable(value = "id") int id, Model m) {
		TeacherAccountDto teacher = service.getEachTeacherById(id);
		m.addAttribute("list", teacher);
		return "roaster-edit";
	}

	@PostMapping("/roaster-list")
	public String edit_roaster(@ModelAttribute("list") TeacherAccountDto list, Model m) {

		accService.updateData(list);
		return "redirect:/admin/class-roster";
	}

	@GetMapping("/stEdit-list/{id}")
	public String edit_Student(@PathVariable(value = "id") int id, Model m) {

		StudentAccountDto student = stdService.getEachStudentById(id);
		m.addAttribute("list", student);
		return "student-edit";
	}

	@PostMapping("/stEdit-list")
	public String upate_list_Student(@ModelAttribute("list") StudentAccountDto list, Model m) {

		stdService.studentUpdate(list);
		return "redirect:/admin/student-list";
	}

	@GetMapping("/deleteStudent/{id}")
	public String deleteStudent(@PathVariable(value = "id") int id) {
		stdService.deleteStudent(id);
		return "redirect:/admin/student-list";
	}

	@GetMapping("/deleteTeacher/{id}")
	public String deleteTeacher(@PathVariable(value = "id") int id) {
		accService.deleteTeacher(id);
		return "redirect:/admin/teacher-list";
	}
	// upload data

	private List<StudentAccountDto> uploadData = new ArrayList<>();

	@GetMapping("/upload_form")
	public String uploadForm() {
		return "upload_form";
	}


	@SuppressWarnings("resource")
	@PostMapping("/upload")
	public String uploadFile(@RequestParam("file") MultipartFile file, Model model) throws IOException {
		try (InputStream inputStream = file.getInputStream()) {

			Workbook workbook = new XSSFWorkbook(inputStream);
			Sheet sheet = workbook.getSheetAt(0);

			List<StudentAccountDto> studentAccounts = new ArrayList<>();

			for (Row row : sheet) {
				if (row.getRowNum() == 0) {
					continue; // Skip header row
				}
				StudentAccountDto student = new StudentAccountDto();

				student.setName(getCellValueAsString(row.getCell(1)));
				student.setEmail(getCellValueAsString(row.getCell(2)));
				student.setRollNo(getCellValueAsString(row.getCell(3)));
				student.setNrcNo(getCellValueAsString(row.getCell(4)));
				student.setDob(getCellValueAsDate(row.getCell(5)));
				student.setPhoneNo(getCellValueAsString(row.getCell(6)));
				student.setMajor(getCellValueAsString(row.getCell(7)));
				student.setRegisterDate(getCellValueAsDate(row.getCell(8)));
				student.setSemester(getCellValueAsString(row.getCell(9)));
				student.setPassword(getCellValueAsString(row.getCell(10)));

				studentAccounts.add(student);
			}

			uploadData = studentAccounts;
			model.addAttribute("filename", file.getOriginalFilename());
			model.addAttribute("excelData", studentAccounts);
		} catch (IOException e) {
			model.addAttribute("message", "Error occurred: " + e.getMessage());
		}
		return "upload_form";
	}

	@PostMapping("/savefile")
	public String saveFile(Model model) {
		for (StudentAccountDto studentDto : uploadData) {
			accService.insertAccountStudent(studentDto);
		}
		uploadData.clear();
		model.addAttribute("message", "Data saved successfully!");
		return "redirect:/admin/student-list";
	}

	private String getCellValueAsString(Cell cell) {
		if (cell == null) {
			return null;
		}
		cell.setCellType(CellType.STRING);
		return cell.getStringCellValue();
	}

	private Date getCellValueAsDate(Cell cell) {
		if (cell == null || cell.getCellType() != CellType.NUMERIC || !DateUtil.isCellDateFormatted(cell)) {
			return null;
		}
		return new Date(cell.getDateCellValue().getTime());
	}
	

	// Semester View
	@GetMapping("/sem-view")
	public String semester_view(Model m) {
		m.addAttribute("sem", semesterService.getAllSemesters());
		return "sem-view";
	}
	// Edit Semester
	@GetMapping("/sem-edit/{id}")
	public String edit_Semester(@PathVariable(value = "id") int id, Model m) {
		Semester sem = semesterRepo.findById(id);
		m.addAttribute("list", sem);
		return "sem-edit";
	}

	@PostMapping("/sem-edit")
	public String upate_Semester(@ModelAttribute("list") Semester list, Model m) {
		semesterRepo.updateSem(list.getStartDate(), list.getEndDate(), list.getId());
		return "redirect:/admin/sem-view";
	}

}
