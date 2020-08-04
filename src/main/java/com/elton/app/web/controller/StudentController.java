package com.elton.app.web.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.elton.app.domain.Student;
import com.elton.app.domain.UserSystem;
import com.elton.app.service.StudentService;
import com.elton.app.service.UserService;

@Controller
@RequestMapping("students")
public class StudentController {

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private UserService userService;
	
    @Autowired
    private MessageSource messageSource;
	
	@GetMapping("/datas")
	public String register(Student student, ModelMap model, @AuthenticationPrincipal User user) {
		student = studentService.findByUserEmail(user.getUsername());
		if (student.hasNotId()) {
			student.setUser(new UserSystem(user.getUsername()));
		}
		model.addAttribute("student", student);
		return "student/register";
	}
	
	@PostMapping("/save")
	public String save(Student student, ModelMap model, @AuthenticationPrincipal User user) {
		UserSystem u = userService.findByEmail(user.getUsername());
		if (userService.isPasswordCorrect(student.getUser().getPassword(), u.getPassword())) {
			student.setUser(u);
			studentService.save(student);
			model.addAttribute("sucess", messageSource.getMessage("label.msg_your_data_has_been_successfully_entered", 
					null, Locale.ENGLISH));
		} else {
			model.addAttribute("failure", messageSource.getMessage("label.msg_your_password_not_match", null, Locale.ENGLISH));
		} 
		return "student/register";
	}	
	
	@PostMapping("/edit")
	public String edit(Student student, ModelMap model, @AuthenticationPrincipal User user) {
		UserSystem u = userService.findByEmail(user.getUsername());
		if (userService.isPasswordCorrect(student.getUser().getPassword(), u.getPassword())) {
			studentService.edit(student);
			model.addAttribute("sucess", messageSource.getMessage("label.msg_your_data_has_been_successfully_entered", 
					null, Locale.ENGLISH));
		} else {
			model.addAttribute("failure", messageSource.getMessage("label.msg_your_data_has_been_successfully_entered", 
					null, Locale.ENGLISH));
		}
		return "student/register";
	}
	
}
