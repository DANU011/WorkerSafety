package com.cos.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //// 스프링에서 관리할수 있게
public class CorsConfig implements WebMvcConfigurer {
	
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true); // 내서버가 응답을 할때 json을 자바스크립트에서 처리할 수 있게 할지를 설정하는 것
		config.addAllowedOrigin("http://localhost:3000"); // 모든 ip에 응답을 허용하겠다
		config.addAllowedHeader("*"); // 모든 header에 응답을 허용하겠다.
		config.addExposedHeader("Authorization");//Authorization 응답허용
		config.addExposedHeader("Refreshtoken");//Refreshtoken 응답허용
		config.addAllowedMethod("*"); // 모든 post,get, delete, patch 요청을 허용하겠다.
		source.registerCorsConfiguration("/**", config);// /**에 들어오는 모든주소는 config의 설정을 따라 하라
		return new CorsFilter(source);
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);
    }
}
