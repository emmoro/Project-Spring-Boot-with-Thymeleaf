package com.elton.app.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.elton.app.domain.UserSystem;

public interface UserService extends UserDetailsService {
	
	/**
	 * Load the user by username
	 * @param String username
	 * @return UserDetails
	 */
	public abstract UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	
	/**
	 * Find all
	 * @param HttpServletRequest request
	 * @return Map<String, Object>
	 */
	public abstract Map<String, Object> findAll(HttpServletRequest request);
	
	/**
	 * Save the user
	 * @param UserSystem user
	 */
	public abstract void saveUser(UserSystem user);
	
	/**
	 * Find by ID
	 * @param Long id
	 * @return UserSystem
	 */
	public abstract UserSystem findById(Long id);
	
	/**
	 * Find by profiles
	 * @param Long userId
	 * @param Long[] profilesId
	 * @return UserSystem
	 */
	public abstract UserSystem findByIdProfiles(Long userId, Long[] profilesId);
	
	/**
	 * Find by email
	 * @param String email
	 * @return UserSystem
	 */
	public abstract UserSystem findByEmail(String email);

	/**
	 * Update the password
	 * @param UserSystem user
	 * @param String password
	 */
	public abstract void updatePassword(UserSystem user, String password);
	
	/**
	 * Valid if password is correct
	 * @param String typedPassword
	 * @param String storedPassword
	 * @return boolean
	 */
	public abstract boolean isPasswordCorrect(String typedPassword, String storedPassword);
	
}
