# WorkerSafety
프로젝트

## 구조
- DA : 데이터 분석 관련
- BE : 인증 및 권한 그리고 API 관련
- FE : 사용자 입력 및 시각화 관련

## 실행
1. DA 서비스 실행
```
$ python app.py
```
2. BE 서비스 실행
```
```
3. FE 서비스 실행
```
```

## 세부사항
- DA => `localhost:5000/api/prediction`
    - HTTP 중에서 POST만 제공
- BE => `localhost:8080/api/...`
    - 권한
    - 인증
    - API 등
- FE => `localhost:3000`
    - 사용자 입력
    - 시각화 등

## 일정 : 4/25 ~ 6/30

### 세부일정 - `유동적으로 변경 가능`
* 1주차
```
계획 작성
```
* 2~3주차
```
DA : 데이터 전처리 & 머신러닝 기반 이상 감지 알고리즘 구현
BE : 모델 호출해서 REST API 생성
FE : UI/UX 설계 및 시각화, 1차 구현
```
* 4~5주차
```
DA : 모델 개선
BE : 스키마 기타 API 확정
FE : UI/UX 확정
```