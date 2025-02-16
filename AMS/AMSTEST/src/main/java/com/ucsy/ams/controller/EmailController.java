package com.ucsy.ams.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ucsy.ams.service.EmailAttendanceService;

@Controller
@RequestMapping("/classRoster")
public class EmailController {
	@Autowired
    private EmailAttendanceService studentAttendanceService;

    @PostMapping("/sendEmails")
    public String sendEmails() {
    	studentAttendanceService.notifyStudentsWithLowAttendance();
        return "redirect:/classRoster/attendance"; 
    }
}
