# time-series / sensor

Data Analysis for Time Series(Sensor) Analysis 

![Python v3.10.4](https://img.shields.io/badge/python-v3.10.4-3670A0?style=flat&logo=python&logoColor=ffdd54)
![pip v23.1.2](https://img.shields.io/badge/pip-v23.1.2-3670A0?style=flat&logo=python&logoColor=ffdd54)

- [가상환경구성하기](#)
- [데이터분석 종합 문서(notebooks)](#)
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
    > Data 분석   
  Data 전처리  
  Model 생성(lr, xgb)  
- [X] Week 3
  - [x] Data set 공유
  - [x] Model 개선
  - [x] README.md 수정

- [X] Week 4
  - [X] 더미 파일 생성 

- [ ] Week 5
  - [X] 머신러닝 모델 확정
    > 시각화  
모델 학습  
모델 선정 

  - [X] 간단한 API를 만들어서 배포
    > CSV 파일을 확정(데이터베이스 구조 확정)  
  간소화된 모델을 선택(백엔드 동기화 성능과 연계)  
  독립변수 및 종속변수와 관련된 전처리 확정. (입력)  
  Flask를 사용해서 "/predict" api를 작동  

  - [ ] 더미 파일 개선
    > Fall-Detection-System 참고해서 fall 데이터 개선  
연령별 맥박, 체온 데이터 개선
    
  - [ ] 비지도 학습
    > 개선된 데이터로 클러스터링  
One-class SVM 모델 생성 후 지도 학습 결과와 비교
    
  - [ ] 전체 연결 시도
  - [ ] README.md & 폴더 정리
  - [ ] 문서 업데이트
  
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


