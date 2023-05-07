package edu.pnu.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
@RestController
public class FlaskController {
	RestTemplate restTemplate = new RestTemplate();
	@PostMapping("/flask/data")
	public  void  receiveData() {
        String url = "http://localhost:5000/flask/data";
    	//HttpHeaders  HTTP 요청 또는 응답의 헤더 정보를 담는 클래스
    	HttpHeaders headers = new HttpHeaders();
    	//headers 객체의 Content-Type 헤더 값을 JSON 형식으로 설정
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	Map<String, Object> data = new HashMap<>();
    	data.put("key", "value");
    	HttpEntity<Map<String, Object>> entity = new HttpEntity<>(data, headers);
    	// HTTP POST 요청을 보내고 응답을 받는 메서드(요청보낼 url,요청에 담을 데이터와 헤더를 담은 객체,요청에 담을 데이터와 헤더를 담은 객체)
    	String response = restTemplate.postForObject(url, entity, String.class);
    	System.out.println(response);
    }
		
//	String requestBody = "{\"name\":\"John\", \"age\":30}";
//	HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
//	String response = restTemplate.postForObject(url, requestEntity, String.class);
	
	
}
