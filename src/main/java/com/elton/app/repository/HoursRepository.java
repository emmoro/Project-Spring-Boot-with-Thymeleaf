package com.elton.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elton.app.domain.Hours;

@Repository
public interface HoursRepository extends JpaRepository<Hours, Long> {

}
