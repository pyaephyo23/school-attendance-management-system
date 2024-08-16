package com.ucsy.ams.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/signin")
	public String login() {
		return "login";
	}

	@GetMapping("/about")
	public String about() {
		return "about";
	}

	@GetMapping("/contact-us")
	public String contact_us() {
		return "contact-us";
	}
}
