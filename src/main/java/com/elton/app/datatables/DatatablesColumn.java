package com.elton.app.datatables;

public class DatatablesColumn {

	public static final String[] INSTRUCTOR = {"id", "name", "codeInstructor", "dtRegister"};
	
	public static final String[] SCHEDULING = {"id", "student.name", "dateConsult", "instructor.name"};

	public static final String[] USERS = {"id", "email", "active", "profiles"};	

}
