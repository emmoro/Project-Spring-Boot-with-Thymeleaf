package com.elton.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elton.app.domain.Student;
import com.elton.app.repository.StudentRepository;
import com.elton.app.service.StudentService;

@Service("studentService")
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Transactional(readOnly = true)
	public Student findByUserEmail(String email) {
		return studentRepository.findByUserEmail(email).orElse(new Student());
	}

	@Transactional(readOnly = false)
	public void save(Student student) {
		studentRepository.save(student);		
	}

	@Transactional(readOnly = false)
	public void edit(Student student) {
		Student p2 = studentRepository.findById(student.getId()).get();
		p2.setName(student.getName());
		p2.setBirthDate(student.getBirthDate());		
	}

}
