package com.cos.jwt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.client.RestTemplate;

import com.cos.jwt.domain.Manager;
import com.cos.jwt.domain.Worker;
import com.cos.jwt.service.ManagerService;
import com.cos.jwt.service.WorkerDetailsService;
import com.cos.jwt.service.WorkerService;
//@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ManagerController {
	@Autowired
	ManagerService managerService;
	
	@Autowired
	WorkerService workerService;
	
	@Autowired
	WorkerDetailsService workerdeDetailsService;
	
	@PostMapping("/user/join")
	public String join(@RequestBody Manager manager) {
		managerService.joinManager(manager);
		return "회원가입완료";
		
	}
	@GetMapping("/worker/list")
	public  ResponseEntity<?> workerlist() {
		List worklist = workerService.workerList();
		return ResponseEntity.ok(worklist);
	}
	
	@PutMapping("/worker/add")
	public void workeradd(@RequestBody Worker worker) {
		 workerService.workeradd(worker);
		 
	}
	
	@DeleteMapping("/worker/delete")
	public void workerdelete(@RequestBody Worker worker) {
		workerService.workerdelete(worker);
	}
	
	@GetMapping("/worker/listdetail")
	public String workerlistdetail(@RequestBody Worker worker) {
		
		return "작업자리스트";
	}
	
	RestTemplate restTemplate = new RestTemplate();
	@PostMapping("/flask/data")
	public  String  receiveData() {
        String url = "http://localhost:5000/predict";
    	//HttpHeaders  HTTP 요청 또는 응답의 헤더 정보를 담는 클래스
    	HttpHeaders headers = new HttpHeaders();
    	//headers 객체의 Content-Type 헤더 값을 JSON 형식으로 설정
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	List list = workerdeDetailsService.WorkerDetailList((double) 1);
    	HttpEntity<List> entity = new HttpEntity<>(list, headers);
    	
    	// HTTP POST 요청을 보내고 응답을 받는 메서드(요청보낼 url,요청에 담을 데이터와 헤더를 담은 객체,요청에 담을 데이터와 헤더를 담은 객체)
    	String response = restTemplate.postForObject(url, entity, String.class);
    	System.out.println(response);
    	return response;
    }
	
//	@GetMapping("/user/username")
//	public ResponseEntity<?> getusername(HttpServletRequest request) {
//		String jwtToken = request.getHeader("Authorization").replace("Bearer ","");
//		String managername = JWT.require(Algorithm.HMAC512("jwt")).build().verify(jwtToken).getClaim("managername").asString();
//		
//		return ResponseEntity.ok(managername);
//	}
	
	
	@PutMapping("/login/logout")
	public void logout(SessionStatus status) {
		status.setComplete();
		
	}
	
}
