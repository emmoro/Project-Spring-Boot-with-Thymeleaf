package com.elton.app.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name = "scheduling") 
public class Scheduling extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5299763477079604840L;
	
	@ManyToOne
	@JoinColumn(name="id_instructor")
	private Instructor instructor;
	
	@ManyToOne
	@JoinColumn(name="id_student")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name="id_hour")
	private Hours hour; 

	@Column(name="consult_date")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dateConsult;

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Hours getHour() {
		return hour;
	}

	public void setHour(Hours hour) {
		this.hour = hour;
	}

	public LocalDate getDateConsult() {
		return dateConsult;
	}

	public void setDateConsult(LocalDate dateConsult) {
		this.dateConsult = dateConsult;
	}

}