package com.cos.api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cos.api.domain.WorkerDetails;
import com.cos.api.repository.WorkerDetailsRepository;

@Service
public class WorkerDetailsService {
	
	
	final WorkerDetailsRepository workerDetailsRepo;
	
	public WorkerDetailsService(WorkerDetailsRepository workerDetailsRepo) {
		this.workerDetailsRepo = workerDetailsRepo;
	}
	
	public List<WorkerDetails> WorkerDetailListTime(LocalDateTime time) {
		List<WorkerDetails> workerDetails = workerDetailsRepo.findByTimeBetween(time.plusNanos((long) (0.1*1_000_000_000)),time.plusSeconds(2)); 
		return workerDetails;
	}
}
