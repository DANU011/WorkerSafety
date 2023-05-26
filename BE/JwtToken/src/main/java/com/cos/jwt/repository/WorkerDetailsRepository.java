package com.cos.jwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.jwt.domain.WorkerDetails;

public interface WorkerDetailsRepository extends JpaRepository<WorkerDetails, Double> {
	public List<WorkerDetails> findByNo(Double no);
}
