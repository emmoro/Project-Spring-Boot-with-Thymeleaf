package com.elton.app.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.elton.app.domain.UserSystem;

@Repository
public interface UserRepository extends JpaRepository<UserSystem, Long> {
	
	@Query("select u from UserSystem u where u.email like :email")  
	Optional<UserSystem> findByEmail(@Param("email") String email);
	
	@Query("select distinct u from UserSystem u "
			+ "join u.profiles p "
			+ "where u.email like :search% OR p.desc like :search%") 
	Page<UserSystem> findByEmailOrProfile(String search, Pageable pageable);

	@Query("select u from UserSystem u "
			+ "join u.profiles p "
			+ "where u.id = :userId AND p.id IN :profileId") 
	Optional<UserSystem> findByIdAndProfiles(Long userId, Long[] profileId);
	
}
