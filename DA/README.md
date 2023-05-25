# time-series / sensor

Data Analysis for Time Series(Sensor) Analysis

![Python v3.10.4](https://img.shields.io/badge/python-v3.10.4-3670A0?style=flat&logo=python&logoColor=ffdd54)
![pip v23.1.2](https://img.shields.io/badge/pip-v23.1.2-3670A0?style=flat&logo=python&logoColor=ffdd54)

- [가상환경구성하기](#)
- [데이터분석 문서(notebooks)](#)
- [Datasets](#dataset-보류)

## 참고자료

- [Fall-Detection-System](#)

## Getting Started

1. 가상환경 실행

```bash
python -m venv venv
venv\Scripts\activate.bat
(venv) pip install -r requirements.txt
```

2. Run on Jupyterlab(Optional)

```bash
jupyter lab
# CMD: cntrl+Z // jupyterlab 종료
```

---

## TODO

- [X] Week 1
  - [X] Flask - Spring Boot 연결  
  
- [x] Week 2
  - [x] 간단한 분석 모델 배포 
    > 데이터 분석   
  데이터 전처리  
  모델 생성(lr, xgb)  
  
- [X] Week 3
  - [x] 데이터 셋 공유
  - [x] 모델 개선
  - [x] README.md 업데이트  

- [X] Week 4
  - [X] 더미 파일 생성
  - [X] 문서 업데이트  

- [ ] Week 5
  - [X] 머신러닝 모델 확정
    > 시각화  
모델 학습  
모델 선정 

  - [X] 간단한 API를 만들어서 배포
    > CSV 파일을 확정 (데이터베이스 구조 확정)  
간소화된 모델을 선택 (백엔드 동기화 성능과 연계)  
독립변수 및 종속변수와 관련된 전처리 확정 (입력)  
Flask를 사용해서 "/predict" api를 작동  

  - [ ] 더미 파일 개선
    > Fall-Detection-System 참고해서 fall 데이터 개선  
연령별 맥박, 체온, 자이로센서 데이터 개선  
맥박, 체온, 낙상 이상치는 비율을 적게  
위도, 경도 랜덤값으로 추가
    
  - [ ] 전체 연결 시도
  - [ ] README.md & 폴더 정리
  - [ ] 문서 업데이트
    > 탐색적 가시화 부분에 그래프 추가  
더미 데이터 생성 방식 추가  
CNN-LSTM 삭제 -> 머신러닝 지도학습 추가  
더미 데이터 헤드 이미지 추가 (이상치 데이터 포함 30%~40%)  


- [ ] Week6
  - [ ] 지도 학습
    > 개선된 데이터로 모델 생성  
F1 값 고려해서 모델 선정 
  - [ ] 비지도 학습
    > 개선된 데이터로 클러스터링  
One-class SVM 모델 생성  
지도 학습 결과와 비교
    
  
## Flask

- [A Comprehensive Guide on using Flask for Data Science](https://www.analyticsvidhya.com/blog/2021/10/a-comprehensive-guide-on-using-flask-for-data-science/)

- [Building a Machine Learning Web Application Using Flask](https://towardsdatascience.com/building-a-machine-learning-web-application-using-flask-29fa9ea11dac)

- [Deploying an ML web app using Flask](https://levelup.gitconnected.com/deploying-ml-web-app-using-flask-334367735777)

```bash
.
└──Flask-scikit
  ├── app.py
  ├── preprocessing.py
  ├──/templates
  │  ├── index.html
  │  └── result.html
  ├── /data
  ├── /notebook
  ├── /model
  │   ├── model.py
  │   ├── imputer.pkl
  │   ├── scaler.pkl
  │   └── model.pkl
  ├── /static
  │   └── style.css
  ├── requiremensts.txt
  └── README.md
```

<img width="500" src="https://github.com/DANU011/Project/blob/main/DA/assets/20230524_1st_result.png"/>
