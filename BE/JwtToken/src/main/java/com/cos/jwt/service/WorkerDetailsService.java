package com.cos.jwt.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cos.jwt.domain.WorkerDetails;
import com.cos.jwt.repository.WorkerDetailsRepository;

@Service
public class WorkerDetailsService {
	
	@Autowired
	WorkerDetailsRepository workerDetailsRepo;
	
	public List<WorkerDetails> WorkerDetailList(int i) {
		List<WorkerDetails> workerDetails = workerDetailsRepo.findByNo(i);
		return workerDetails;
	}
	public List<WorkerDetails> WorkerDetailList20(int i) {
		List<WorkerDetails> workerDetails = workerDetailsRepo.findByNoBetween(i,i+19); 
		return workerDetails;
	}
	public List<WorkerDetails> WorkerDetailListTime(LocalDateTime time) {
		List<WorkerDetails> workerDetails = workerDetailsRepo.findByTimeBetween(time,time.plusSeconds(2)); 
		return workerDetails;
	}
}
