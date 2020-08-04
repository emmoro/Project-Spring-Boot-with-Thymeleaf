package com.elton.app.domain;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "instructor")
public class Instructor extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8760942043332421858L;

	@Column(name = "name", unique = true, nullable = false)
	private String name;
	
	@Column(name = "code_instructor", unique = true, nullable = false)
	private Integer codeInstructor;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "date_register", nullable = false)
	private LocalDate dtRegister;
	
	@JsonIgnore
	@OneToMany(mappedBy = "instructor")
	private List<Scheduling> schedulings;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "id_user")
	private UserSystem user;
	
	public Instructor() {
		super();
	}

	public Instructor(Long id) {
		super.setId(id);
	}

	public Instructor(UserSystem user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCodeInstructor() {
		return codeInstructor;
	}

	public void setCodeInstructor(Integer codeInstructor) {
		this.codeInstructor = codeInstructor;
	}

	public LocalDate getDtRegister() {
		return dtRegister;
	}

	public void setDtRegister(LocalDate dtRegister) {
		this.dtRegister = dtRegister;
	}

	public List<Scheduling> getSchedulings() {
		return schedulings;
	}

	public void setSchedulings(List<Scheduling> schedulings) {
		this.schedulings = schedulings;
	}

	public UserSystem getUser() {
		return user;
	}

	public void setUser(UserSystem user) {
		this.user = user;
	}

}

