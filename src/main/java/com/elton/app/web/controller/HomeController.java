package com.elton.app.web.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
    @Autowired
    private MessageSource messageSource;

	@GetMapping({"/", "/home"})
	public String home(HttpServletRequest request) {
		return "home";
	}

	@GetMapping({"/login"})
	public String login() {
		return "login";
	}	
	
	@GetMapping({"/login-error"})
	public String loginError(ModelMap model) {
		model.addAttribute("alert", messageSource.getMessage("label.error", null, Locale.ENGLISH));
		model.addAttribute("title", messageSource.getMessage("label.title_error", null, Locale.ENGLISH));
		model.addAttribute("text", messageSource.getMessage("label.text_error", null, Locale.ENGLISH));
		model.addAttribute("subtext", messageSource.getMessage("label.sub_text_error", null, Locale.ENGLISH));
		return "login";
	}

	@GetMapping({"/access-denied"})
	public String accessDenied(ModelMap model, HttpServletResponse resp) {
		model.addAttribute("status", resp.getStatus());
		model.addAttribute("error", messageSource.getMessage("label.access_allowed", null, Locale.ENGLISH));
		model.addAttribute("message", messageSource.getMessage("label.allowed_mensage", null, Locale.ENGLISH));
		return "error";
	}
	
}
