package com.cos.jwt.config.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.jwt.domain.Manager;
// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를
// 스프링 시큐리티의 고유한 세션저장소에 저장을 해준다.
public class PrincipalDetails implements UserDetails{
	
	private Manager manager;
	
	public PrincipalDetails(Manager manager) {
		this.manager = manager;
	}
	
	public Manager getmanager() {
		return manager;
		
	}
	
	// 계정의 권한을 리턴 
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
			return null;
	}
	// 
	@Override
	public String getPassword() {
		return manager.getPassword();
	}

	@Override
	public String getUsername() {
		return manager.getManagerid();
	}
	//계정이 만료되지 않았는지 리턴한다.(true: 만료안됨)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	//계정이 잠겨있는지 안잠겨있는지 리턴(true: 안잠겨져있음)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	// 비밀번호가 만료되지 않았는지 리턴(true:만료안됨)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	// 계정활성화(사용가능)인지 리턴한다.(true: 활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	
}
