package com.cos.api.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.client.RestTemplate;

import com.cos.api.domain.Manager;
import com.cos.api.domain.Worker;
import com.cos.api.domain.WorkerDetails;
import com.cos.api.service.ManagerService;
import com.cos.api.service.WorkerAnalysisSerivice;
import com.cos.api.service.WorkerDetailsService;
import com.cos.api.service.WorkerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@EnableScheduling
@RestController
public class ManagerController   {
	
	final ManagerService managerService;

	final WorkerService workerService;
	
	final WorkerDetailsService workerdeDetailsService;	
	
	final WorkerAnalysisSerivice workerAnalysisSerivice;
	
	@Autowired
	public ManagerController(ManagerService managerService,WorkerService workerService,WorkerDetailsService workerdeDetailsService,WorkerAnalysisSerivice workerAnalysisSerivice) {
		this.managerService = managerService;
		this.workerService = workerService;
		this.workerdeDetailsService = workerdeDetailsService;
		this.workerAnalysisSerivice = workerAnalysisSerivice;
	
	}
	
	@PostMapping("/user/join")
	public ResponseEntity<String> join(@RequestBody Manager manager) {
		managerService.joinManager(manager);
		return ResponseEntity.ok("회원가입완료");
		
	}
	@GetMapping("/worker/list")
	public  ResponseEntity<?> workerlist() {
		List<Worker> worklist = workerService.workerList();
		return ResponseEntity.ok(worklist);
	}
	
	@PutMapping("/worker/add")
	public void workeradd(@RequestBody Worker worker) {
		 workerService.workeradd(worker);
		 
	}
	
	@DeleteMapping("/worker/delete")
	public void workerdelete(@RequestParam Integer userCode) {
		workerService.workerdelete(userCode);
	}
	
	
	RestTemplate restTemplate = new RestTemplate();
	LocalDateTime time = LocalDateTime.of(2023, 6, 15, 1, 0, 2, 0);
	private volatile boolean scheduled = false;
	@Scheduled(fixedRate = 2000)
	@PostMapping("/worker/listdetail")
	public ResponseEntity<?> workerlistdetail() throws JsonMappingException, JsonProcessingException {
		if (scheduled) {
        String url = "http://localhost:5000/predict";
    	//HttpHeaders  HTTP 요청 또는 응답의 헤더 정보를 담는 클래스
    	HttpHeaders headers = new HttpHeaders();
    	//headers 객체의 Content-Type 헤더 값을 JSON 형식으로 설정
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	List<WorkerDetails> list = workerdeDetailsService.WorkerDetailListTime(time);
    	System.out.println(list);
    	
        LocalDateTime updatedTime = time.plusSeconds(2);
        time = updatedTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = time.format(formatter);
        System.out.println(formattedTime);
        
    	HttpEntity<List<WorkerDetails>> entity = new HttpEntity<>(list, headers);
    	// HTTP POST 요청을 보내고 응답을 받는 메서드(요청보낼 url,요청에 담을 데이터와 헤더를 담은 객체,요청에 담을 데이터와 헤더를 담은 객체)
    	String response = restTemplate.postForObject(url, entity, String.class);
    	System.out.println(response);
    	//플라스크에서 온 분석한 데이터 데이터베이스에 저장 
//    	workerAnalysisSerivice.saveWorkerAnalysisData(response, updatedTime);
    	return ResponseEntity.ok(response);
		}
		return null;
			
    }

    @PostMapping("/worker/start")
    public ResponseEntity<?> startScheduledTask() {
        scheduled = true;
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/worker/stop")
    public ResponseEntity<?> stopScheduledTask() {
        scheduled = false;
        return ResponseEntity.ok().build();
    }

	
	@PutMapping("/login/logout")
	public void logout(SessionStatus status) {
		status.setComplete();
		
	}
	
	
}
