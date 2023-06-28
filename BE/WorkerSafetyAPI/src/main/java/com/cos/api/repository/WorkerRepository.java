package com.cos.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.api.domain.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Integer> {
	
	public void deleteByuserCode(Integer code);
	
}
