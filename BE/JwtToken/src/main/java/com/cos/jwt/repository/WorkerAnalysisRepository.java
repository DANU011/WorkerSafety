package com.cos.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.jwt.domain.WorkerAnalysis;

public interface WorkerAnalysisRepository extends JpaRepository<WorkerAnalysis, Integer> {

}
