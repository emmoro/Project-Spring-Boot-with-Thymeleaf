package com.elton.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.elton.app.domain.Instructor;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
	
	@Query("select i from Instructor i where i.user.id = :id")
	Optional<Instructor> findByUserId(Long id);
	
	@Query("select i from Instructor i where i.user.email like :email")
	Optional<Instructor> findByUserEmail(String email);
	
	@Query("select i from Instructor i where i.id = :id")
	Instructor findByIdInstructor(Long id);

}
