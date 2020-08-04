package com.elton.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.elton.app.domain.ProfileType;
import com.elton.app.service.UserService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final String ADMIN = ProfileType.ADMIN.getDesc();
    private static final String INSTRUCTOR = ProfileType.INSTRUCTOR.getDesc();
    private static final String STUDENT = ProfileType.STUDENT.getDesc();
	
	@Autowired
	private UserService userService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
			//public access released
			.antMatchers("/webjars/**", "/css/**", "/image/**", "/js/**").permitAll()
			.antMatchers("/", "/home").permitAll()
			.antMatchers("/u/new/register", "/u/new/realized", "/u/new/student/save").permitAll()
			.antMatchers("/u/confirmation/register").permitAll()
			.antMatchers("/u/p/**").permitAll()
			
			//private access admin
			.antMatchers("/u/edit/password", "/u/confirm/password").hasAnyAuthority(INSTRUCTOR, STUDENT)
			.antMatchers("/u/**").hasAuthority(ADMIN)
			
			//private access instructor
			.antMatchers("/instructors/datas", "/instructors/save", "/instructors/edit").hasAnyAuthority(INSTRUCTOR, ADMIN)
			.antMatchers("/instructor/**").hasAuthority(INSTRUCTOR)
			
			//private access student
			.antMatchers("/students/**").hasAuthority(STUDENT)			
			
			.anyRequest().authenticated()
			.and()
				.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/", true)
				.failureUrl("/login-error")
				.permitAll()
			.and()
				.logout()
				.logoutSuccessUrl("/")
			.and()
				.exceptionHandling()
				.accessDeniedPage("/access-denied");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
}
