package com.cos.jwt.Conponent;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cos.jwt.domain.WorkerDetails;
import com.cos.jwt.service.WorkerDetailsService;


@Component
public class WorkerDeteilComponent {
	@Autowired
	private WorkerDetailsService workerdeDetailsService;
	
    private RestTemplate restTemplate = new RestTemplate();
    private int counter =1;

    @Scheduled(fixedRate = 2000)
    public void sendWorkerListDetail() {
        String url = "http://localhost:5000/predict";
    	//HttpHeaders  HTTP 요청 또는 응답의 헤더 정보를 담는 클래스
        HttpHeaders headers = new HttpHeaders();
    	//headers 객체의 Content-Type 헤더 값을 JSON 형식으로 설정
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	Integer no = counter++;
    	List<WorkerDetails> list = workerdeDetailsService.WorkerDetailList(no);
    	HttpEntity<List<WorkerDetails>> entity = new HttpEntity<>(list, headers);
    	
    	// HTTP POST 요청을 보내고 응답을 받는 메서드(요청보낼 url,요청에 담을 데이터와 헤더를 담은 객체,요청에 담을 데이터와 헤더를 담은 객체)
    	String response = restTemplate.postForObject(url, entity, String.class);
    	System.out.println(response);
    	//return ResponseEntity.ok(response);
    }
   
}

