package com.elton.app.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user", indexes = {@Index(name = "id_user_email", columnList = "email")})
public class UserSystem extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1030066073167687282L;

	@Column(name = "email", unique = true, nullable = false)
	private String email;
	
	@JsonIgnore
	@Column(name = "password", nullable = false)
	private String password;
	
	@ManyToMany
	@JoinTable(
		name = "user_has_profile", 
        joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, 
        inverseJoinColumns = { @JoinColumn(name = "profile_id", referencedColumnName = "id") }
	)
	private List<Profile> profiles;
	
	@Column(name = "active", nullable = false, columnDefinition = "TINYINT(1)")
	private boolean active;
	
	@Column(name = "checker_code", length = 6)
	private String checkerCode;
	
	public UserSystem() {
		super();
	}

	public UserSystem(Long id) {
		super.setId(id);
	}

	public void addPerfil(ProfileType type) {
		if (this.profiles == null) {
			this.profiles = new ArrayList<>();
		}
		this.profiles.add(new Profile(type.getCod()));
	}
	
	public UserSystem(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getCheckerCode() {
		return checkerCode;
	}

	public void setCheckerCode(String checkerCode) {
		this.checkerCode = checkerCode;
	}
	
}
