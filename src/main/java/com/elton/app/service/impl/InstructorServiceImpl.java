package com.elton.app.service.impl;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elton.app.domain.Instructor;
import com.elton.app.repository.InstructorRepository;
import com.elton.app.service.InstructorService;

@Service("instructorService")
public class InstructorServiceImpl implements InstructorService {
	
	@Autowired
	private InstructorRepository instructorRepository;
	
	@Transactional(readOnly = true)
	@Override
	public Instructor findByUserId(Long id) {
		return instructorRepository.findByUserId(id).orElse(new Instructor());
	}

	@Transactional(readOnly = false)
	@Override
	public void save(Instructor instructor) {
		instructorRepository.save(instructor);
	}

	@Transactional(readOnly = false)
	@Override
	public void edit(Instructor instructor) {
		Instructor i2 = instructorRepository.findById(instructor.getId()).get();
		i2.setCodeInstructor(instructor.getCodeInstructor());
		i2.setDtRegister(instructor.getDtRegister());
		i2.setName(instructor.getName());
	}
	
	@Transactional(readOnly = true)
	@Override
	public Instructor findByEmail(String email) {
		return instructorRepository.findByUserEmail(email).orElse(new Instructor());
	}

	@Override
	public List<Instructor> getAllInstructors() {
		return instructorRepository.findAll();
	}
	
	@Override
	public Instructor findInstructorById(Long id) {
		return instructorRepository.findByIdInstructor(id);
	}
	
}
