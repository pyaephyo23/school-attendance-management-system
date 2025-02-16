package com.ucsy.ams.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ucsy.ams.dto.GetTimeSlotBySemesterIdDTO;
import com.ucsy.ams.dto.StudentRollCallDTO;
import com.ucsy.ams.entity.Account;
import com.ucsy.ams.entity.Students;
import com.ucsy.ams.repository.AccountRepo;
import com.ucsy.ams.repository.CourseRepo;
import com.ucsy.ams.repository.StudentRepo;
import com.ucsy.ams.service.StudentService;
import java.lang.reflect.Type;

@Controller
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private AccountRepo accRepo;

	@Autowired
	private StudentRepo studentRepo;

	@Autowired
	private CourseRepo courseRepo;

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@ModelAttribute
	public void commonAcc(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			Account acc = accRepo.findByEmail(email);
			Optional<Students> st = studentRepo.findById(acc.getId());
			m.addAttribute("user", acc);
			m.addAttribute("titles", "Student DashBoard");
			m.addAttribute("semName", st.get().getSemester());
			m.addAttribute("major", st.get().getMajor());
			m.addAttribute("totalCourse", courseRepo.findByCourseStudentId(acc.getId()).size());
			m.addAttribute("condition", false);
			m.addAttribute("check", true);

			LocalDate date = LocalDate.now();
			int year = date.getYear();
			int year1 = year - 1;
			m.addAttribute("dt", year);
			m.addAttribute("dt1", year1);
			m.addAttribute("pwd1", false);
			m.addAttribute("pwd2", false);
			m.addAttribute("pwd3", true);
			m.addAttribute("z1", false);
			m.addAttribute("z2", true);
		}
	}

	@GetMapping({ "/dash", "/dashboard" })
	public String dash(Model model) {
		Account acc = (Account) model.getAttribute("user");
		Optional<Students> std = studentRepo.findById(acc.getId());
		List<GetTimeSlotBySemesterIdDTO> result = studentService.getTimeSlotBySemesterId(std.get().getSemester(),
				std.get().getId());

		Gson gson = new Gson();
		Type listType = new TypeToken<List<GetTimeSlotBySemesterIdDTO>>() {
		}.getType();
		String jsonArray = gson.toJson(result, listType);

		String title = (String) model.getAttribute("sem");
		model.addAttribute("jsonArray", jsonArray);
		model.addAttribute("title", title + " Time Table");
		return "teacher-student-dashboard";
	}

	@GetMapping("rollCall")
	public String rollCall(Model model) {
		LocalDate date = LocalDate.now();
		int month = date.getMonthValue();
		int year = date.getYear();
		String months = String.valueOf(month - 1);
		String years = String.valueOf(year);

		Account acc = (Account) model.getAttribute("user");
		Optional<Students> student = studentRepo.findById(acc.getId());

		String startDate = years + "-" + months + "-1";
		String endDate = years + "-" + month + "-31";
		List<StudentRollCallDTO> result = studentService.findAttendanceByStudentAndCourse(startDate, endDate,
				student.get().getId(), student.get().getSemester());

		for (StudentRollCallDTO each : result) {
			float percentage = ((float) each.getCurrentAttendance() / (float) each.getTotalAttendance()) * 100;
			each.setPercentage(String.format("%.2f", percentage));

			float mark = ((float) each.getCurrentAttendance() / (float) each.getTotalAttendance()) * 5;
			each.setMark(String.format("%.2f", mark));
		}

		System.out.println(result.toString());
		model.addAttribute("attendance", result);
		return "ShowStudentRollCall";
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
			return "redirect:/student/dash";
		}
}
