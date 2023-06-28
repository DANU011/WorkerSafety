package com.cos.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.api.domain.Manager;

public interface ManagerRepository extends JpaRepository<Manager, String> {
	
	public Manager findByManagerid(String managerid);

}
