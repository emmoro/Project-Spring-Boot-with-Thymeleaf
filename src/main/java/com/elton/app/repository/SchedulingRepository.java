package com.elton.app.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.elton.app.domain.Hours;
import com.elton.app.domain.Scheduling;
import com.elton.app.util.HistoryStudent;

@Repository
public interface SchedulingRepository extends JpaRepository<Scheduling, Long> {
	
	@Query("select h "
			+ "from Hours h "
			+ "where not exists("
				+ "select a.hour.id "
					+ "from Scheduling a "
					+ "where "
						+ "a.instructor.id = :id and "
						+ "a.dateConsult = :data and "
						+ "a.hour.id = h.id "
			+ ") "
			+ "order by h.hourMinute asc")
	List<Hours> findSchedulesNotScheduledInstructorIdAndDate(Long id, LocalDate data);

	@Query("select a.id as id,"
				+ "a.student as student,"
				+ "CONCAT(a.dateConsult, ' ', a.hour.hourMinute) as dateConsult,"
				+ "a.instructor as instructor "
			+ "from Scheduling a "
			+ "where a.student.user.email like :email")
	Page<HistoryStudent> findHistoryByStudentEmail(String email, Pageable pageable);

	@Query("select a.id as id,"
			+ "a.student as student,"
			+ "CONCAT(a.dateConsult, ' ', a.hour.hourMinute) as dateConsult,"
			+ "a.instructor as instructor "
		+ "from Scheduling a "
		+ "where a.instructor.user.email like :email")	
	Page<HistoryStudent> findHistoryByInstructorEmail(String email, Pageable pageable);

	@Query("select a from Scheduling a "
			+ "where "
			+ "	(a.id = :id AND a.student.user.email like :email) "
			+ " OR "
			+ " (a.id = :id AND a.instructor.user.email like :email)")
	Optional<Scheduling> findByIdStudentOrInstructorEmail(Long id, String email);

}
