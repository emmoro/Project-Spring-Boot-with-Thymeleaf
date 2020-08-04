package com.elton.app.web.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.userdetails.User;

import com.elton.app.domain.Instructor;
import com.elton.app.domain.UserSystem;
import com.elton.app.service.InstructorService;
import com.elton.app.service.UserService;

@Controller 
@RequestMapping("instructors")
public class InstructorController {
	
	@Autowired
	private InstructorService instructorService;
	
	@Autowired
	private UserService userService;
	
    @Autowired
    private MessageSource messageSource;

	@GetMapping({"/datas"})
	public String openByInstructor(Instructor instructor, ModelMap model, @AuthenticationPrincipal User user) {
		if (instructor.hasNotId()) {
			instructor = instructorService.findByEmail(user.getUsername());
			model.addAttribute("instructor", instructor);
		}
		return "instructor/register";
	}
	
	@PostMapping({"/save"})
	public String save(Instructor instructor, RedirectAttributes attr, @AuthenticationPrincipal User user) {
		if (instructor.hasNotId() && instructor.getUser().hasNotId()) {
			UserSystem us = userService.findByEmail(user.getUsername());
			instructor.setUser(us);
		}
		instructorService.save(instructor);
		attr.addFlashAttribute("sucess", messageSource.getMessage("label.message_sucess", null, Locale.ENGLISH));
		attr.addFlashAttribute("instructor", instructor);
		return "redirect:/instructors/datas";
	}
	
	@PostMapping({"/edit"})
	public String edit(Instructor instructor, RedirectAttributes attr) {
		instructorService.edit(instructor);
		attr.addFlashAttribute("sucess", messageSource.getMessage("label.message_sucess", null, Locale.ENGLISH));
		attr.addFlashAttribute("instructor", instructor);
		return "redirect:/instructors/datas";		
	}
	
	@SuppressWarnings("deprecation")
	@GetMapping("/specialties/id/{id}")
	public ResponseEntity<?> getInstructorsById(@PathVariable("id") String id) {
		return ResponseEntity.ok(instructorService.findInstructorById(new Long(id)));
	}
	
}
