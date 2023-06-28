package com.cos.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.api.domain.WorkerDetails;

public interface WorkerDetailsRepository extends JpaRepository<WorkerDetails, Integer> {
	public List<WorkerDetails> findByTimeBetween(LocalDateTime start,LocalDateTime end);
}
