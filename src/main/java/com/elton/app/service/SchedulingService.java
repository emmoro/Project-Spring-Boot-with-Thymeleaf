package com.elton.app.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.elton.app.domain.Hours;
import com.elton.app.domain.Scheduling;

public interface SchedulingService {
	
	/**
	 * Find schedules not scheduled instructor date
	 * @param Long id
	 * @param LocalDate date
	 * @return List<Hours>
	 */
	public abstract List<Hours> findSchedulesNotScheduledInstructorIdAndDate(Long id, LocalDate date);
	
	/**
	 * Save the scheduling
	 * @param Scheduling scheduling
	 */
	public abstract void save(Scheduling scheduling);
	
	/**
	 * Find history by student email
	 * @param String email
	 * @param HttpServletRequest request
	 * @return Map<String, Object>
	 */
	public abstract Map<String, Object> findHistoryByStudentEmail(String email, HttpServletRequest request);
	
	/**
	 * Find history by instructor email
	 * @param String email
	 * @param HttpServletRequest request
	 * @return Map<String, Object>
	 */
	public abstract Map<String, Object> findHistoryByInstructorEmail(String email, HttpServletRequest request);
	
	/**
	 * Find by id
	 * @param Long id
	 * @return Scheduling
	 */
	public abstract Scheduling findById(Long id);
	
	/**
	 * Edit the scheduling
	 * @param Scheduling scheduling
	 * @param String email
	 */
	public abstract void edit(Scheduling scheduling, String email);
	
	/**
	 * Find by user ID
	 * @param Long id
	 * @param String email
	 * @return Scheduling
	 */
	public abstract Scheduling findByIdUser(Long id, String email);
	
	/**
	 * Delete the scheduling
	 * @param Long id
	 */
	public abstract void delete(Long id);

}
