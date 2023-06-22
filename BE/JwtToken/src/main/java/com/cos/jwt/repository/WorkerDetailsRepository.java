package com.cos.jwt.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.jwt.domain.WorkerDetails;

public interface WorkerDetailsRepository extends JpaRepository<WorkerDetails, Integer> {
	public List<WorkerDetails> findByNo(Integer no);
	public List<WorkerDetails> findByNoBetween(Integer start,Integer end);
	public List<WorkerDetails> findByTimeBetween(LocalDateTime start,LocalDateTime end);
	//List<WorkerDetails> findAllByOrderByTime(LocalTime time);
	List<WorkerDetails> findByTime(LocalDateTime time);
}
