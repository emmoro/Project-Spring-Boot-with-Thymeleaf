package com.elton.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.elton.app.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	
	@Query("select s from Student s where s.user.email like :email")
	Optional<Student> findByUserEmail(String email);

}
