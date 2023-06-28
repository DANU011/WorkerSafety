package com.cos.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.api.domain.WorkerAnalysis;

public interface WorkerAnalysisRepository extends JpaRepository<WorkerAnalysis, Integer> {

}
