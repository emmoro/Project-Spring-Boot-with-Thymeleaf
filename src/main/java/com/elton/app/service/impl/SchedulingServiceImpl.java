package com.elton.app.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elton.app.datatables.Datatables;
import com.elton.app.datatables.DatatablesColumn;
import com.elton.app.domain.Hours;
import com.elton.app.domain.Scheduling;
import com.elton.app.exception.AccessDeniedException;
import com.elton.app.repository.SchedulingRepository;
import com.elton.app.service.SchedulingService;
import com.elton.app.util.HistoryStudent;

@Service("schedulingService")
public class SchedulingServiceImpl implements SchedulingService {
	
	@Autowired
	private SchedulingRepository schedulingRepository;
	
	@Autowired
	private Datatables datatables;

	@Transactional(readOnly = true)
	public List<Hours> findSchedulesNotScheduledInstructorIdAndDate(Long id, LocalDate date) {
		return schedulingRepository.findSchedulesNotScheduledInstructorIdAndDate(id, date);
	}

	@Transactional(readOnly = false)
	public void save(Scheduling scheduling) {
		schedulingRepository.save(scheduling);
	}

	@Transactional(readOnly = true)
	public Map<String, Object> findHistoryByStudentEmail(String email, HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColumn.SCHEDULING);
		Page<HistoryStudent> page = schedulingRepository.findHistoryByStudentEmail
				(email, datatables.getPageable());
		return datatables.getResponse(page);
	}

	@Transactional(readOnly = true)
	public Map<String, Object> findHistoryByInstructorEmail(String email, HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColumn.SCHEDULING);
		Page<HistoryStudent> page = schedulingRepository.findHistoryByInstructorEmail
				(email, datatables.getPageable());
		return datatables.getResponse(page);
	}

	@Transactional(readOnly = true)
	public Scheduling findById(Long id) {
		return schedulingRepository.findById(id).get();
	}

	@Transactional(readOnly = false)
	public void edit(Scheduling scheduling, String email) {
		Scheduling ag = findByIdUser(scheduling.getId(), email);
		ag.setDateConsult(scheduling.getDateConsult());
		ag.setHour(scheduling.getHour());
		ag.setInstructor(scheduling.getInstructor());
				
	}

	@Transactional(readOnly = true)
	public Scheduling findByIdUser(Long id, String email) {
		return schedulingRepository
				.findByIdStudentOrInstructorEmail(id, email)
				.orElseThrow(() -> new AccessDeniedException("Acesso negado ao usuário: " + email));
	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		schedulingRepository.deleteById(id);
	}

}
