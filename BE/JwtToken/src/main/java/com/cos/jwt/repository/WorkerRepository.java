package com.cos.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.jwt.domain.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Integer> {
	
	public void deleteByuserCode(Integer userCode);
	
}
