package com.cos.jwt.service;

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
}
