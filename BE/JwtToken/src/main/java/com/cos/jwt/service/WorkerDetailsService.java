package com.cos.jwt.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cos.jwt.domain.WorkerDetails;
import com.cos.jwt.repository.WorkerDetailsRepository;

@Service
public class WorkerDetailsService {
	
	@Autowired
	WorkerDetailsRepository workerDetailsRepo;
	
	public List<WorkerDetails> WorkerDetailListTime(LocalDateTime time) {
		List<WorkerDetails> workerDetails = workerDetailsRepo.findByTimeBetween(time.plusNanos((long) (0.1*1_000_000_000)),time.plusSeconds(2)); 
		return workerDetails;
	}
}
