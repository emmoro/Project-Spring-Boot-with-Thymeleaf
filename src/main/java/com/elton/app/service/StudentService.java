package com.elton.app.service;

import com.elton.app.domain.Student;

public interface StudentService {
	
	/**
	 * Find by user email
	 * @param String email
	 * @return Student
	 */
	public abstract Student findByUserEmail(String email);
	
	/**
	 * Save the student
	 * @param Student student
	 */
	public abstract void save(Student student);
	
	/**
	 * Edit the student
	 * @param Student student
	 */
	public abstract void edit(Student student);

}
