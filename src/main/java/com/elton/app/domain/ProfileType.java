package com.elton.app.domain;

public enum ProfileType {
	
	ADMIN(1, "ADMIN"), INSTRUCTOR(2, "INSTRUCTOR"), STUDENT(3, "STUDENT");
	
	private Long cod;
	private String desc;

	private ProfileType(long cod, String desc) {
		this.cod = cod;
		this.desc = desc;
	}

	public long getCod() {
		return cod;
	}

	public String getDesc() {
		return desc;
	}

}
