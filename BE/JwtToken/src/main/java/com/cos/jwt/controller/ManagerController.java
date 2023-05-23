package com.cos.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;

import com.cos.jwt.domain.Manager;
import com.cos.jwt.service.ManagerService;
//@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ManagerController {
	@Autowired
	ManagerService managerService;
	
	@PostMapping("/user/join")
	public String join(@RequestBody Manager manager) {
		managerService.joinManager(manager);
		return "회원가입완료";
		
	}
	@GetMapping("home")
	public String home() {
		return "<h1>home</h1>";
	}
	
	@PostMapping("token")
	public String token() {
		return "<h1>token</h1>";
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Manager manager) {
	    // 입력된 회원 정보의 유효성 검사
	    if (manager.getManagerid() == null || manager.getManagerid().isEmpty()) {
	        return ResponseEntity.badRequest().body("아이디를 입력해주세요.");
	    }
	    if (manager.getPassword() == null || manager.getPassword().isEmpty()) {
	        return ResponseEntity.badRequest().body("비밀번호를 입력해주세요.");
	    }
	    
	    Manager findMember = managerService.getManager(manager);
	    
	    if (findMember != null && findMember.getPassword().equals(manager.getPassword())) {
	        return ResponseEntity.ok(findMember);
	    } else {
	        return ResponseEntity.badRequest().body("아이디나 비밀번호가 일치하지 않습니다.");
	    }
	}
	
	@GetMapping("/logout")
	public void logout(SessionStatus status) {
		status.setComplete();
		
	}
	
}
