package com.elton.app.util;

import com.elton.app.domain.Instructor;
import com.elton.app.domain.Student;

public interface HistoryStudent {
	
	Long getId();
	
	Student getStudent();
	
	String getDateConsult();
	
	Instructor getInstructor();

}
