package com.cos.jwt.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cos.jwt.domain.Manager;
import com.cos.jwt.repository.ManagerRepository;

@Service
public class ManagerService {
	
	@Autowired
	ManagerRepository managerRepo;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public Manager joinManager (Manager manager) {
		manager.setPassword(bCryptPasswordEncoder.encode(manager.getPassword()));
		return managerRepo.save(manager);
	}
	
	public Manager getManager (Manager manager) {
		//Optional Repository에서 리턴 타입을 Optional로 바로 받을 수 있도록하는것
		Manager findManager = managerRepo.findByManagerid(manager.getManagerid());
		return findManager;
	}
}
