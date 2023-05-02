package edu.pnu.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import edu.pnu.domain.Member;
import edu.pnu.service.MemberService;
@CrossOrigin(origins = "http://localhost:3000")

@SessionAttributes("member")
//객체 받기위해 view 받으려면 Controller
@RestController
public class MemberController {
	// MemberService를 스프링에 등록 의존성 
	@Autowired
	private MemberService memberService;
	
	@PostMapping("/login")
	public Member login(@RequestBody Member member) {
		Member findMember = memberService.getMember(member);
		
		if (findMember != null && findMember.getPassword().equals(member.getPassword())) {
			
			return findMember;
		}

		else {
			return null;
		}  
	}
	
	@GetMapping("/logout")
	public void logout(SessionStatus status) {
		status.setComplete();
		
	}
	
}
