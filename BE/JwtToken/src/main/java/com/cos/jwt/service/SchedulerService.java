package com.cos.jwt.service;

import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cos.jwt.domain.WorkerDetails;

@Service
public class SchedulerService {
	@Autowired
	WorkerDetailsService workerdeDetailsService;
	
	RestTemplate restTemplate = new RestTemplate();
	
	private final HttpClient httpClient;
	
	private int counter = 1;
	// 로그인 후 작업을 시작하는 메서드
	
	private Map<String, Object> savedData = new HashMap<>();

	private CompletableFuture<Map<String, Object>> taskFuture;
	
	
	 public SchedulerService() {
	        this.httpClient = HttpClient.newHttpClient();
	    }
	
	public void startWorkerListDetail() {
	    // ScheduledExecutorService 생성
	    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
	    
	    // scheduleAtFixedRate 메서드를 사용하여 작업을 반복 실행
	    // this::sendWorkerListDetail은 람다 표현식으로 sendWorkerListDetail 메서드를 실행하는 역할을 합니다.
	    // 0은 초기 지연 시간을 의미하며, 작업이 바로 시작되도록 설정합니다.
	    // 2는 작업 간의 간격을 의미하며, 2초마다 작업이 반복되도록 설정합니다.
	    // TimeUnit.SECONDS는 작업 간의 간격의 단위를 지정합니다.
	    executorService.scheduleAtFixedRate(this::sendWorkerListDetail, 0, 2, TimeUnit.SECONDS);
//	    Map<String, Object> map = sendWorkerListDetail();
	    // taskFuture가 null이면 작업이 실행 중이지 않으므로 새 작업을 예약합니다.
        if (taskFuture == null) {
            taskFuture = CompletableFuture.supplyAsync(this::sendWorkerListDetail, executorService);

            // 작업이 완료되면 taskFuture를 null로 설정하여 다음 작업이 예약될 수 있도록 합니다.
            taskFuture.thenRun(() -> taskFuture = null);
        }
	}
//	public Map<String, Object> getSavedData() {
//		 // taskFuture가 null이면 작업이 실행 중이 아니므로 null을 반환합니다.
//        if (taskFuture == null) {
//            return null;
//        }
//
//        // 작업이 완료되었는지 확인하고, 완료되었을 경우 저장된 데이터를 반환합니다.
//        if (taskFuture.isDone()) {
//            try {
//                return taskFuture.get();
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
//
//        // 작업이 아직 완료되지 않았을 경우 null을 반환합니다.
//        return null;
//    }


	// 실제 요청을 보내는 메서드
	public Map<String, Object> sendWorkerListDetail() {
	    // 요청을 보낼 URL
		String url = "http://localhost:5000/predict";
    	//HttpHeaders  HTTP 요청 또는 응답의 헤더 정보를 담는 클래스
    	HttpHeaders headers = new HttpHeaders();
    	//headers 객체의 Content-Type 헤더 값을 JSON 형식으로 설정
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	Integer no = counter++;
    	List<WorkerDetails> list = workerdeDetailsService.WorkerDetailList(no);
    	HttpEntity<List<WorkerDetails>> entity = new HttpEntity<>(list, headers);
    	System.out.println(list);
    	// HTTP POST 요청을 보내고 응답을 받는 메서드(요청보낼 url,요청에 담을 데이터와 헤더를 담은 객체,요청에 담을 데이터와 헤더를 담은 객체)
    	String response = restTemplate.postForObject(url, entity, String.class);
    	System.out.println("response"+response);
    	Map<String, Object> data = new HashMap<>();
    	data.put("list", list);
    	data.put("response", response);
    	savedData = data;
    	// ResponseEntity를 반환하여 응답 데이터 전달
    	return data;
//    	try {
//    		HttpRequest request = HttpRequest.newBuilder()
//                    .uri(new URI(url))
//                    .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(entity)))
//                    .header("Content-Type", "application/json")
//                    .build();
//    		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//        	System.out.println(response);
//        	// 응답 및 리스트 데이터를 Map에 담기
//        	Map<String, Object> data = new HashMap<>();
//        	data.put("list", list);
//        	data.put("response", response);
//        	// ResponseEntity를 반환하여 응답 데이터 전달
//    	} catch (IOException | InterruptedException | URISyntaxException e) {
//            e.printStackTrace();
//        }
    	
	}

}
