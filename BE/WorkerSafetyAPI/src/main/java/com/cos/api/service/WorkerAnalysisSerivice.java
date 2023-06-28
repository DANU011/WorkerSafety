package com.cos.api.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cos.api.domain.Worker;
import com.cos.api.domain.WorkerAnalysis;
import com.cos.api.repository.WorkerAnalysisRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WorkerAnalysisSerivice {
	
	final WorkerAnalysisRepository workAnalysisRepo;
	
	final WorkerService workerService;
	
	@Autowired
	public WorkerAnalysisSerivice(WorkerAnalysisRepository workAnalysisRepo,WorkerService workerService) {
		this.workAnalysisRepo = workAnalysisRepo;
		this.workerService = workerService;
	}
	
	public void saveWorkerAnalysisData(String response, LocalDateTime time) throws JsonMappingException, JsonProcessingException {
		// 응답 JSON 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readValue(response, JsonNode.class);
        JsonNode listNode = responseJson.get("list");

        // 각 WorkerAnalysis 객체를 저장
        for (JsonNode workerNode : listNode) {
            WorkerAnalysis workerAnalysis = new WorkerAnalysis();
            int userCode = workerNode.get("userCode").asInt();

            // Worker 객체를 userCode 값으로 검색합니다.
            Worker worker = workerService.workerid(userCode).orElse(null);
            System.out.println(worker);
            // worker가 null이 아닌 경우에만 userCode를 설정합니다.
            if (worker != null) {
                workerAnalysis.setUserCode(worker);
                // 나머지 필드 값을 설정합니다.
                workerAnalysis.setHeartbeat(workerNode.get("heartbeat").asInt());
                workerAnalysis.setTemp(workerNode.get("temp").asDouble());
                workerAnalysis.setPrediction(workerNode.get("prediction").asText());
                workerAnalysis.setTime(time);
                System.out.println(workerAnalysis);
                // 리포지토리를 사용하여 WorkerAnalysis 객체를 저장합니다.
                workAnalysisRepo.save(workerAnalysis);
            }
        }

    }
	
}
