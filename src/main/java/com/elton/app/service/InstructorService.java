package com.elton.app.service;

import java.util.List;

import com.elton.app.domain.Instructor;

public interface InstructorService {
	
	/**
	 * Find by user for ID
	 * @param Long id
	 * @return Instructor
	 */
	public abstract Instructor findByUserId(Long id);
	
	/**
	 * Save the instructor
	 * @param Instructor instructor
	 */
	public abstract void save(Instructor instructor);
	
	/**
	 * Edit the instructor
	 * @param Instructor instructor
	 */
	public abstract void edit(Instructor instructor);
	
	/**
	 * Find by email
	 * @param String email
	 * @return Instructor
	 */
	public abstract Instructor findByEmail(String email);
	
	/**
	 * Get all instructos
	 * @return List<Instructor>
	 */
	public abstract List<Instructor> getAllInstructors();
	
	/**
	 * Find instructor by ID
	 * @param Long id
	 * @return Instructor
	 */
	public abstract Instructor findInstructorById(Long id);

}
