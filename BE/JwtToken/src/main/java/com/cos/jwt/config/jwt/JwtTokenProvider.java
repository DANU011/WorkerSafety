package com.cos.jwt.config.jwt;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.jwt.domain.Manager;

public class JwtTokenProvider {
	
	
    // Access Token과 Refresh Token을 발급할 때 사용할 시크릿 키
    //private static final String SECRET_KEY = "yourSecretKey";

    // Access Token의 유효시간 (30분)
    private static final long ACCESS_TOKEN_EXPIRATION_MS = 10 * 60 * 1000;

    // Refresh Token의 유효시간 (7일)
    private static final long REFRESH_TOKEN_EXPIRATION_MS = 7 * 24 * 60 * 60 * 1000;

    // Access Token 생성
    public static String generateAccessToken(Manager manager) {
        return generateToken(manager, ACCESS_TOKEN_EXPIRATION_MS);
    }

    // Refresh Token 생성
    public static String generateRefreshToken(Manager manager) {
//    	RefreshToken refreshToken =  new RefreshToken();;
//    	refreshToken.setId(manager.getManagerid());
//    	refreshToken.setRefreshToken(generateToken(manager, REFRESH_TOKEN_EXPIRATION_MS));
//    	refreshTokenRepository.save(refreshToken);
        return generateToken(manager, REFRESH_TOKEN_EXPIRATION_MS);
    }

    // 토큰 생성 공통 메소드
    private static String generateToken(Manager manager, long expirationTime) {
        // 토큰에 담을 클레임(Claims) 설정
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("managerid", manager);

//        // 현재 시간 기준으로 토큰의 만료 시간 설정
//        Date now = new Date();
//        Date expiration = new Date(now.getTime() + expirationTime);

        // JWT 토큰 생성
//        return Jwt.builder()
//                .setClaims(claims)
//                .setIssuedAt(now)
//                .setExpiration(expiration)
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
//                .compact();
        
        
		 return JWT.create()
		//토큰이름
		.withSubject("jwt토큰")
		//토큰 만료시간 60000 == 1분
		.withExpiresAt(new Date(System.currentTimeMillis()+(expirationTime)))
		//비공개 클래임
		.withClaim("managerid", manager.getManagerid())
		.withClaim("managername", manager.getName())
		//서버만 아는 고유한 값으로 해야함
		.sign(Algorithm.HMAC512("jwt"));
    }

    // 토큰에서 managerid 추출
//    public static String extractManagerId(String token) {
//        Claims claims = Jwts.parser()
//                .setSigningKey(SECRET_KEY)
//                .parseClaimsJws(token)
//                .getBody();
//        return claims.get("managerid", String.class);
//    }
}
