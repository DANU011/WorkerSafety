package com.cos.api.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.api.domain.Manager;
import com.cos.api.repository.ManagerRepository;

//http://localhost:8080/login -> 여기서 동작을 안한다
//->직접 PrincipalDetailService 사용하는 필터 JwtAuthenticationFilter를 만들어 준다.

@Service  // bean 등록
public class PrincipalDetailService implements UserDetailsService {
	@Autowired
	private ManagerRepository managerRepo;
	
	// 스프링이 로그인 요청을 가로챌때, username, password 변수 2개를 가로채는데 
	// password 부분 처리는 알아서 함.
	// username이 DB에 있는지만 확인해주면 됨
	@Override
	public UserDetails loadUserByUsername(String managerid) throws UsernameNotFoundException {
		System.out.println("PrincipalDetailsService : 진입"+managerid);
		Manager manager = managerRepo.findByManagerid(managerid);
		System.out.println("userEntity:"+ manager);
		return new PrincipalDetails(manager); // 시큐리티의 세션에 유저정보가 저장이 됨

	}

}
