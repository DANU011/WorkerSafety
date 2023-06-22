package com.cos.jwt.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.cos.jwt.domain.Manager;
import com.cos.jwt.domain.Objectchange;
import com.cos.jwt.domain.Worker;
import com.cos.jwt.domain.WorkerDetails;
import com.cos.jwt.service.ManagerService;
import com.cos.jwt.service.WorkerDetailsService;
import com.cos.jwt.service.WorkerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//@CrossOrigin(origins = "http://localhost:3000")
@EnableScheduling
@RestController
public class ManagerController   {
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


        LocalDateTime updatedTime = time.plusSeconds(2);
        time = updatedTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = time.format(formatter);
        System.out.println(formattedTime);
    	HttpEntity<List<WorkerDetails>> entity = new HttpEntity<>(list, headers);
    	System.out.println(list);
    	// HTTP POST 요청을 보내고 응답을 받는 메서드(요청보낼 url,요청에 담을 데이터와 헤더를 담은 객체,요청에 담을 데이터와 헤더를 담은 객체)
    	String response = restTemplate.postForObject(url, entity, String.class);
    	System.out.println("response"+response);
    	System.out.println(response);
    	//json 형태의 response를 객체로 
    	ObjectMapper objectMapper = new ObjectMapper();
    	Objectchange objectchange = objectMapper.readValue(response, Objectchange.class);
    	System.out.println("objectchange"+objectchange);
    	
    	// 응답 및 리스트 데이터를 Map에 담기
    	Map<String, Object> data = new HashMap<>();
    	data.put("list", list);
    	data.put("response", objectchange);
    	// ResponseEntity를 반환하여 응답 데이터 전달
    	return ResponseEntity.ok(data);
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
	    
//	@GetMapping("/refresh")
//    public ResponseEntity<Map<String, String>> refreshAccessToken(@RequestParam("refreshToken") String refreshToken) {
//        try {
//            // 리프레시 토큰 검증
//            if (JwtTokenProvider.validateRefreshToken(refreshToken)) {
//                // 리프레시 토큰으로부터 사용자 정보 가져오기
//                String managerId = JwtTokenProvider.getManagerIdFromToken(refreshToken);
//                Manager managerEntity = managerRepo.findByManagerid(managerId);
//
//                if (managerEntity != null) {
//                    // 새로운 액세스 토큰 생성
//                    String accessToken = JwtTokenProvider.generateAccessToken(managerEntity);
//
//                    // 응답 생성
//                    Map<String, String> response = new HashMap<>();
//                    response.put("accessToken", accessToken);
//
//                    return ResponseEntity.ok(response);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // 토큰 갱신 실패
//        return ResponseEntity.badRequest().build();
//    }
	
	
	@PutMapping("/login/logout")
	public void logout(SessionStatus status) {
		status.setComplete();
		
	}
	
	
}
