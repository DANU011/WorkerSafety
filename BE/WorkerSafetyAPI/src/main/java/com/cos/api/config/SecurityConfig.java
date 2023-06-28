package com.cos.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cos.api.config.jwt.JwtAuthenticationFilter;
import com.cos.api.config.jwt.JwtAuthorizationFilter;
import com.cos.api.repository.ManagerRepository;

@Configuration  // 빈(bean) 정의를 생성하고 관리하는 역할
@EnableWebSecurity  //  시큐리티 활성화 -> 기본 스프링 필터체인에 등록

public class SecurityConfig {
	
	@Autowired
	public CorsConfig corsConfig;
	
	@Autowired
	private ManagerRepository managerRepo;
	
	// 암호화 시키기
	@Bean
	public BCryptPasswordEncoder paswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.csrf().disable() // CSRF 보호를 비활성화
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// Seesion을 사용하지 않는다
				.and()
				.formLogin().disable()// formLogin을 쓰지 않는다.
				.httpBasic().disable()// httpBasic을 쓰지 않는다.
				.apply(new MyCustomDsl()) // 커스텀 필터 등록
				.and()
				.authorizeRequests(authroize -> authroize .antMatchers("/user/join").permitAll()
						.antMatchers("/login").permitAll()
						//메서드를 사용하여 요청에 대한 권한 설정 /user/join만 접근허용 
						.anyRequest().authenticated())//나머지 접근제한 
				.build();
	}
	public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
		@Override
		public void configure(HttpSecurity http) throws Exception {
			AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
			http
					.addFilter(corsConfig.corsFilter())// @CrossOrigin(인증x),시큐리티 필터에 등록 인증(ㅇ)
					.addFilter(new JwtAuthenticationFilter(authenticationManager))// AuthenticationManger
					.addFilter(new JwtAuthorizationFilter(authenticationManager, managerRepo));
		}
	}
}
