package com.cos.jwt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;

import com.cos.jwt.domain.Manager;
import com.cos.jwt.domain.Worker;
import com.cos.jwt.service.ManagerService;
import com.cos.jwt.service.WorkerService;
//@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ManagerController {
	@Autowired
	ManagerService managerService;
	
	@Autowired
	WorkerService workerService;
	
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
