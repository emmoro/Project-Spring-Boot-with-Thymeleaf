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
@Table(name = "student")
public class Student extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4208885328419545670L;
	
	@Column(name = "name", unique = true, nullable = false)
	private String name;

	@Column(name = "birth_date", nullable = false)
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate birthDate;

	@JsonIgnore
	@OneToMany(mappedBy = "student")
	private List<Scheduling> schedulings;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "id_user")
	private UserSystem user;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
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
