package com.elton.app.web.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.elton.app.domain.Hours;
import com.elton.app.domain.Instructor;
import com.elton.app.domain.ProfileType;
import com.elton.app.domain.Scheduling;
import com.elton.app.domain.Student;
import com.elton.app.service.HoursService;
import com.elton.app.service.InstructorService;
import com.elton.app.service.SchedulingService;
import com.elton.app.service.StudentService;

@Controller 
@RequestMapping("schedulings")
public class SchedulingController {
	
	@Autowired
	private SchedulingService schedulingService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private InstructorService instructorService;
	
	@Autowired
	private HoursService hoursService;
	
    @Autowired
    private MessageSource messageSource;

	@GetMapping({"/schedule"})
	public String scheduleAppointment(Scheduling scheduling, Model model) {

		List<Instructor> instructors = instructorService.getAllInstructors();
		model.addAttribute("instructors", instructors);
		
		List<Hours> hours = hoursService.getHours();
		model.addAttribute("hours", hours);
		
		return "scheduling/register";		
	}
	
	@GetMapping("/allInstructor")
	public ResponseEntity<?> getSchedules() {
		return ResponseEntity.ok(instructorService.getAllInstructors());
	}
	
	@GetMapping("/schedule/instructor/{id}/date/{date}")
	public ResponseEntity<?> getSchedules(@PathVariable("id") Long id,
										 @PathVariable("date") @DateTimeFormat(iso = ISO.DATE) LocalDate date) {
		return ResponseEntity.ok(schedulingService.findSchedulesNotScheduledInstructorIdAndDate(id, date));
	}
	
	@PostMapping({"/save"})
	public String save(Scheduling scheduling, RedirectAttributes attr, @AuthenticationPrincipal User user) {
		Student student = studentService.findByUserEmail(user.getUsername());
		Instructor instructor = instructorService.findInstructorById(scheduling.getInstructor().getId());
		scheduling.setInstructor(instructor);
		scheduling.setStudent(student);
		schedulingService.save(scheduling);
		
		attr.addFlashAttribute("sucess", messageSource.getMessage("label.appointment_has_been_successfully_scheduled", 
				null, Locale.ENGLISH));
		return "redirect:/schedulings/schedule";		
	}
	
	@GetMapping({"/historic/student", "/historic/querys"})
	public String historic() {

		return "scheduling/historic-student";
	}
	
	@GetMapping("/datatables/server/historic")
	public ResponseEntity<?> historicSchedulesByStudent(HttpServletRequest request, @AuthenticationPrincipal User user) {
		if (user.getAuthorities().contains(new SimpleGrantedAuthority(ProfileType.STUDENT.getDesc()))) {
			
			return ResponseEntity.ok(schedulingService.findHistoryByStudentEmail(user.getUsername(), request));
		}
		
		if (user.getAuthorities().contains(new SimpleGrantedAuthority(ProfileType.INSTRUCTOR.getDesc()))) {
			
			return ResponseEntity.ok(schedulingService.findHistoryByInstructorEmail(user.getUsername(), request));
		}		
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/edit/query/{id}") 
	public String preEditQueryStudent(@PathVariable("id") Long id, 
										    ModelMap model, @AuthenticationPrincipal User user) {
		
		Scheduling scheduling = schedulingService.findByIdUser(id, user.getUsername());
		
		List<Instructor> instructors = instructorService.getAllInstructors();
		model.addAttribute("instructors", instructors);

		List<Hours> hours = hoursService.getHours();
		model.addAttribute("hours", hours);
		
		model.addAttribute("scheduling", scheduling);
		return "scheduling/register";
	}
	
	@PostMapping("/edit")
	public String editQuery(Scheduling scheduling, RedirectAttributes attr, @AuthenticationPrincipal User user) {
		schedulingService.edit(scheduling, user.getUsername());
		attr.addFlashAttribute("sucesso", messageSource.getMessage("label.your_appointment_has_been_successfully_changed", 
				null, Locale.ENGLISH));
		
		return "redirect:/schedulings/schedule";
	}
	
	@GetMapping("/delete/query/{id}")
	public String deletAppointment(@PathVariable("id") Long id, RedirectAttributes attr) {
		schedulingService.delete(id);
		
		attr.addFlashAttribute("sucesso", messageSource.getMessage("label.appointment_successfully_deleted", 
				null, Locale.ENGLISH));
		
		return "redirect:/schedulings/historic/student";
	}

}
