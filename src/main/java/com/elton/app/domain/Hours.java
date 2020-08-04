package com.elton.app.domain;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "hours", indexes = {@Index(name = "id_minute_hour", columnList = "minute_hour")})
public class Hours extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9072499029032889417L;
	
	@Column(name = "minute_hour", unique = true, nullable = false)
	private LocalTime hourMinute;

	public LocalTime getHourMinute() {
		return hourMinute;
	}

	public void setHourMinute(LocalTime hourMinute) {
		this.hourMinute = hourMinute;
	}
	
}