package com.cos.jwt.config.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.cos.jwt.config.auth.PrincipalDetails;
import com.cos.jwt.domain.Manager;
import com.cos.jwt.domain.RefreshToken;
import com.cos.jwt.repository.ManagerRepository;
import com.cos.jwt.repository.RefreshTokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

//시큐리티가 filter가지고 있는데 그 필터중에 BasicAuthenticationfilter 라는 것이 있음.
//권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터를 무조건 타게 되어있음.
//만약에 권한이 인증이 필요한 주소가 아니라면 이 필터를 안탐
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
//	@Autowired
//	private RefreshTokenRepository refreshTokenRepository;
	
	private ManagerRepository managerRepo;
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, ManagerRepository managerRepo) {
		super(authenticationManager);
		this.managerRepo = managerRepo;
	}
//	protected void setErrorResponse(HttpServletResponse res, String message) throws IOException {
//			ObjectMapper objectMapper = new ObjectMapper();
//			 Map<String, String> responseMap = new HashMap<>();
//			    
//	        res.setStatus(HttpStatus.UNAUTHORIZED.value());
//	        res.setContentType("application/json; charset=UTF-8");
//	        responseMap.put("message", message);
//	        String json = objectMapper.writeValueAsString(responseMap);
//	        res.getWriter().write(json);
//	    }

		@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
		        throws IOException, ServletException {
		    System.out.println("인증이나 권한이 필요한 주소 요청이 됨.");
		
		    String jwtHeader = request.getHeader("Authorization");
		    System.out.println("jwtHeader:" + jwtHeader);
		
		    try {
		        // header가 있는지 확인
		        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
		            chain.doFilter(request, response);
		            return;
		        }
		        // JWT 토큰을 검증하여 사용자 인증
		        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
		        String managerid = JWT.require(Algorithm.HMAC512("jwt")).build().verify(jwtToken).getClaim("managerid").asString();
		
		        // 서명이 정상적으로 됨, 사용자가 인증됨
		        if (managerid != null) {
		            Manager managerEntity = managerRepo.findByManagerid(managerid);
		            PrincipalDetails principalDetails = new PrincipalDetails(managerEntity);
		
		            // Authentication 객체를 강제로 만들어서 SecurityContextHolder에 저장
		            Authentication authentication =
		                    new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
		            SecurityContextHolder.getContext().setAuthentication(authentication);
		
		            
		        }
		    } catch (IllegalArgumentException e) {
		        logger.error("유효하지 않은 토큰");		        
		    } catch (TokenExpiredException e) {
		        logger.warn("토큰 기한 만료");
		     // Refresh Token 검증
		        String jwtrefreshToken = request.getHeader("Refreshtoken");
		    	String refreshedManagerid = JWT.require(Algorithm.HMAC512("jwt")).build().verify(jwtrefreshToken).getClaim("managerid").asString();
		    	
//		    	Optional<RefreshToken> refreshToken = refreshTokenRepository.findById(refreshedManagerid);
		    	
//		    	if (refreshToken.get().getRefreshToken().equals(jwtrefreshToken)) {
		    	if (refreshedManagerid != null) {
		    		Manager managerEntity = managerRepo.findByManagerid(refreshedManagerid);

		    		PrincipalDetails principalDetails = new PrincipalDetails(managerEntity);

		    		// Refresh Token 검증 성공 시 인증 처리
		    		Authentication authentication =
		    				new UsernamePasswordAuthenticationToken(principalDetails, null,principalDetails.getAuthorities());
		    		SecurityContextHolder.getContext().setAuthentication(authentication);

		    		// 새로운 Access Token 발급
		    		String newAccessToken = JwtTokenProvider.generateAccessToken(principalDetails.getmanager());
		    		response.addHeader("Authorization", "Bearer " + newAccessToken);
		    		
//					Map<String, String> responseMap = new HashMap<>();
//					ObjectMapper objectMapper = new ObjectMapper();
//					
//					response.setStatus(HttpStatus.UNAUTHORIZED.value());
//					response.setContentType("application/json; charset=UTF-8");
//			        responseMap.put("message", "access토큰재발급");
//			        String json = objectMapper.writeValueAsString(responseMap);
//			        response.getWriter().write(json);
		    		
		    	} else {
		    		// Refresh Token 검증 실패 시 인증 처리 실패로 처리
		    		SecurityContextHolder.clearContext();
		    	}
		        
		    } catch (JWTDecodeException e) {
		        logger.error("토큰 형식 오류.");
		        
		    }
		    // 다음 필터로 요청 전달
            chain.doFilter(request, response);
		}
			
	
//	//인증이나 권한이 필요한 주소요청이 있을 때 해당 필터를 타게됨
//		@Override
//		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//				throws IOException, ServletException {
//			//뒤쪽에도 있어서 응답이 두번나서 오류 지워야함
//			//super.doFilterInternal(request, response, chain);
//			
//			System.out.println("인증이나 권한이 필요한 주소 요청이 됨.");
//			
//			String jwtHeader = request.getHeader("Authorization");
//			System.out.println("jwtHeader:"+ jwtHeader);
//			
//			try {							
//				//header가 있는지 확인
//				if(jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
//					chain.doFilter(request, response);
//					return;
//				}//JWT 토큰을 검증을 해서 정상적인 사용자인지 확인
//				String jwtToken = request.getHeader("Authorization").replace("Bearer ","");
//				
//				String managerid = JWT.require(Algorithm.HMAC512("jwt")).build().verify(jwtToken).getClaim("managerid").asString();
//				//서명이 정상적으로 됨 , 사용자가 인증됐음
//				if(managerid != null) {
//					Manager managerEntity = managerRepo.findByManagerid(managerid);
//					
//					PrincipalDetails principalDetails = new PrincipalDetails(managerEntity);
//					//Authentication객체를 강제로 만들기 
//					//Jwt토큰 서명을 통해서 서명이 정상이면 Authentication객체를 만들어준다..
//					Authentication authentication = 
//							new UsernamePasswordAuthenticationToken(principalDetails, null,principalDetails.getAuthorities());
//					
//					//강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장
//					SecurityContextHolder.getContext().setAuthentication(authentication);
//					}
//				}catch (IllegalArgumentException e) {
//		                logger.error("유효하지 않은 토큰");
//		                setErrorResponse(response, "유효하지 않은 토큰");
//		            } catch (TokenExpiredException e) {
//		                logger.warn("토큰 기한 만료");
//		                setErrorResponse(response, "토큰 기한 만료");
//		            }catch(JWTDecodeException e){
//		                logger.error("토큰 형식 오류.");
//		                setErrorResponse(response, "토큰 형식 오류");
//		            }
//			
//			 chain.doFilter(request, response);
//			
//			
//			//header가 있는지 확인
//			if(jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
//				chain.doFilter(request, response);
//				return;
//			}//JWT 토큰을 검증을 해서 정상적인 사용자인지 확인
//			String jwtToken = request.getHeader("Authorization").replace("Bearer ","");
//			
//			String managerid = JWT.require(Algorithm.HMAC512("jwt")).build().verify(jwtToken).getClaim("managerid").asString();
//			//서명이 정상적으로 됨 , 사용자가 인증됐음
//			if(managerid != null) {
//				Manager managerEntity = managerRepo.findByManagerid(managerid);
//				
//				PrincipalDetails principalDetails = new PrincipalDetails(managerEntity);
//				//Authentication객체를 강제로 만들기 
//				//Jwt토큰 서명을 통해서 서명이 정상이면 Authentication객체를 만들어준다..
//				Authentication authentication = 
//						new UsernamePasswordAuthenticationToken(principalDetails, null,principalDetails.getAuthorities());
//				
//				//강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장
//				SecurityContextHolder.getContext().setAuthentication(authentication);
//				
//				chain.doFilter(request, response);
//			}
//		}
}
