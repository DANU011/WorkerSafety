package com.cos.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cos.api.domain.Manager;
import com.cos.api.repository.ManagerRepository;

@Service
public class ManagerService {
	
	final ManagerRepository managerRepo;
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public ManagerService(ManagerRepository mangerRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.managerRepo = mangerRepo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	public Manager joinManager (Manager manager) {
		manager.setPassword(bCryptPasswordEncoder.encode(manager.getPassword()));
		return managerRepo.save(manager);
	}
	
	public Manager getManager (Manager manager) {
		Manager findManager = managerRepo.findByManagerid(manager.getManagerid());
		return findManager;
	}
}
