package com.cos.jwt.controller;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.client.RestTemplate;

import com.cos.jwt.domain.Manager;
import com.cos.jwt.domain.Worker;
import com.cos.jwt.domain.WorkerDetails;
import com.cos.jwt.service.ManagerService;
import com.cos.jwt.service.WorkerDetailsService;
import com.cos.jwt.service.WorkerService;
//@CrossOrigin(origins = "http://localhost:3000")
//@EnableScheduling
@RestController
public class ManagerController {
	@Autowired
	ManagerService managerService;
	
	@Autowired
	WorkerService workerService;
	
	@Autowired
	WorkerDetailsService workerdeDetailsService;
	
//	@Autowired
//	WorkerDeteilComponent workerDeteilComponent;
	
	
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
	
	
	RestTemplate restTemplate = new RestTemplate();
	int counter = 1;
//	 @Scheduled(fixedRate = 2000)
	@PostMapping("/worker/listdetail")
	public ResponseEntity<?> workerlistdetail() {
//		WorkerDeteilComponent workerDeteilComponent;
//		workerDeteilComponent.sendWorkerListDetail();	
        String url = "http://localhost:5000/predict";
    	//HttpHeaders  HTTP 요청 또는 응답의 헤더 정보를 담는 클래스
    	HttpHeaders headers = new HttpHeaders();
    	//headers 객체의 Content-Type 헤더 값을 JSON 형식으로 설정
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	Integer no = counter++;
    	List<WorkerDetails> list = workerdeDetailsService.WorkerDetailList(no);
    	HttpEntity<List<WorkerDetails>> entity = new HttpEntity<>(list, headers);
    	System.out.println(list);
    	// HTTP POST 요청을 보내고 응답을 받는 메서드(요청보낼 url,요청에 담을 데이터와 헤더를 담은 객체,요청에 담을 데이터와 헤더를 담은 객체)
    	String response = restTemplate.postForObject(url, entity, String.class);
    	System.out.println(response);
    	
    	return ResponseEntity.ok(list+response);
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
