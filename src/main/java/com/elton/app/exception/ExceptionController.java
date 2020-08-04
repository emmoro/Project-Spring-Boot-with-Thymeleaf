package com.elton.app.exception;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionController {
	
    @Autowired
    private MessageSource messageSource;
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ModelAndView userNotFindException(UsernameNotFoundException ex) {
		ModelAndView model = new ModelAndView("error");
		model.addObject("status", 404);
		model.addObject("error", messageSource.getMessage("label.message_sucess", null, Locale.ENGLISH));
		model.addObject("message", ex.getMessage());
		return model;
	}

}