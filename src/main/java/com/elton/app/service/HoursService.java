package com.elton.app.service;

import java.util.List;

import com.elton.app.domain.Hours;

public interface HoursService {
	
	/**
	 * Get hours
	 * @return List<Hours>
	 */
	public abstract List<Hours> getHours();

}
