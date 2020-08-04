package com.elton.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "profile")
public class Profile extends AbstractEntity {

	@Column(name = "description", nullable = false, unique = true)
	private String desc;
	
	public Profile() {
		super();
	}

	public Profile(Long id) {
		super.setId(id);
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
