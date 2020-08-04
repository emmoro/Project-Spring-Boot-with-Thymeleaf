package com.elton.app.web.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.elton.app.domain.Instructor;
import com.elton.app.domain.Profile;
import com.elton.app.domain.ProfileType;
import com.elton.app.domain.UserSystem;
import com.elton.app.service.InstructorService;
import com.elton.app.service.UserService;

@Controller
@RequestMapping("u")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private InstructorService instructorService;
	
    @Autowired
    private MessageSource messageSource;
	
    @GetMapping("/new/register/user")
    public String registerByAdminForInstructorAndStudent(UserSystem userSystem) {
        return "user/register";
    }
    
    @GetMapping("/list")
    public String listUsers() {
        return "user/list";
    }  

    @GetMapping("/datatables/server/users")
    public ResponseEntity<?> listUsersDatables(HttpServletRequest request) {
        return ResponseEntity.ok(userService.findAll(request));
    } 
    
    @PostMapping("/register/save")
    public String saveUsers(UserSystem userSystem, RedirectAttributes attr) {
    	List<Profile> perfis = userSystem.getProfiles();
    	if (perfis.size() > 2 || 
    			perfis.containsAll(Arrays.asList(new Profile(1L), new Profile(3L))) ||
    			perfis.containsAll(Arrays.asList(new Profile(2L), new Profile(3L)))) {
    		attr.addFlashAttribute("failure", messageSource.getMessage("label.msg_student_not_admin_instructor", 
    				null, Locale.ENGLISH));    		
    		attr.addFlashAttribute("userSystem", userSystem);
    	} else {
    		try {
    			userService.saveUser(userSystem);
    			attr.addFlashAttribute("sucess", messageSource.getMessage("label.message_sucess", null, Locale.ENGLISH));
    		} catch (DataIntegrityViolationException ex) {
    			attr.addFlashAttribute("failure", messageSource.getMessage("label.msg_email_existing", null, Locale.ENGLISH));
			}
    	}
    	return "redirect:/u/new/register/user";
    }
    
    @GetMapping("/edit/credentials/user/{id}")
    public ModelAndView preEditCredentials(@PathVariable("id") Long id) {

        return new ModelAndView("user/register", "userSystem", userService.findById(id));
    }    
    
    @GetMapping("/edit/datas/user/{id}/profiles/{profiles}")
    public ModelAndView preEditRegisterPersonalData(@PathVariable("id") Long userId, 
    												   @PathVariable("profiles") Long[] profilesId) {
    	UserSystem us = userService.findByIdProfiles(userId, profilesId);
    	
    	if (us.getProfiles().contains(new Profile(ProfileType.ADMIN.getCod())) &&
    		!us.getProfiles().contains(new Profile(ProfileType.INSTRUCTOR.getCod())) ) {
    		
    		return new ModelAndView("user/register", "user", us);
    	} else if (us.getProfiles().contains(new Profile(ProfileType.INSTRUCTOR.getCod()))) {
    		
    		Instructor instructor = instructorService.findByUserId(userId);
    		return instructor.hasNotId()
    				? new ModelAndView("instructor/register", "instructor", new Instructor(userId))
    				: new ModelAndView("instructor/register", "instructor", instructor);
    	} else if (us.getProfiles().contains(new Profile(ProfileType.STUDENT.getCod()))) {
    		ModelAndView model = new ModelAndView("error");
    		model.addObject("status", 403);
    		model.addObject("error", messageSource.getMessage("label.msg_restricted_area", null, Locale.ENGLISH));
    		model.addObject("message", messageSource.getMessage("label.msg_student_restricted", null, Locale.ENGLISH));
    		return model;
    	}
    	
        return new ModelAndView("redirect:/u/list");
    }
    
    @GetMapping("/edit/password")
    public String openEditPassword() {
    	return "user/edit-password";
    }
    
    @PostMapping("/confirm/password")
    public String editPassword(@RequestParam("password1") String s1, @RequestParam("password2") String s2, 
    						  @RequestParam("password3") String s3, @AuthenticationPrincipal User user,
    						  RedirectAttributes attr) {
    	
    	if (!s1.equals(s2)) {
    		attr.addFlashAttribute("failure", messageSource.getMessage("label.msg_Passwords_dont_match", 
    				null, Locale.ENGLISH));
    		return "redirect:/u/edit/password";
    	}
    	
    	UserSystem u = userService.findByEmail(user.getUsername());
    	if(!userService.isPasswordCorrect(s3, u.getPassword())) {
    		attr.addFlashAttribute("failure", messageSource.getMessage("label.msg_current_password_dont_match", 
    				null, Locale.ENGLISH));
    		return "redirect:/u/editar/password";
    	}
    		
    	userService.updatePassword(u, s1);
    	attr.addFlashAttribute("sucess", messageSource.getMessage("label.msg_password_changed", 
				null, Locale.ENGLISH));
    	return "redirect:/u/edit/password";
    }

}
