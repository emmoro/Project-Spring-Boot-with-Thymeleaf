package com.elton.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elton.app.domain.Hours;
import com.elton.app.repository.HoursRepository;
import com.elton.app.service.HoursService;

@Service("hoursService")
public class HoursServiceImpl implements HoursService {

	@Autowired
	private HoursRepository hoursRepository;
	
	@Override
	public List<Hours> getHours() {
		return hoursRepository.findAll();
	}
}
