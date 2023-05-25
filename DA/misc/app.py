from flask import Flask, request, jsonify
import joblib
import traceback
import pandas as pd
import numpy as np

app = Flask(__name__)

@app.route('/api/predict', methods=['POST'])
def predict(): # 모델 예측을 위한 함수, HTTP POST 요청을 처리
    if classifier: # 학습된 분류기(classifier)가 있을 경우에만 예측 수행
        try:
            content = request.json
            # HTTP POST 요청으로 전송된 JSON 형식의 데이터를 가져옴. request 객체는 Flask에서 제공하는 클래스로 HTTP 요청 정보를 저장
            print(content)
            Sex = content['Sex']
            Age = content['Age']
            Pclass = content['Pclass']
            # JSON 형식으로 전송된 데이터에서 각 필드에 해당하는 값을 가져옴.
            input = [[Sex, Age, Pclass]] # 입력 데이터를 2차원 리스트로
            print('raw: ', input)
            input_ct = ct.transform(input)
            input_sc = sc.transform(input_ct)
            # 입력 데이터에 대해, 카테고리컬 변수를 라벨 인코딩하는 등의 전처리를 수행. ct와 sc는 이전에 학습된 전처리기.
            prediction = classifier.predict(input_sc)
            return jsonify({'prediction': str(prediction)})
            # 전처리된 데이터를 사용하여 분류기에 입력으로 넣고 예측 결과를 반환. jsonify 함수를 사용하여 JSON 형식으로 결과를 반환.
        except:
            return jsonify({'trace': traceback.format_exc()})
    else:
        print('Model not found')
        return('Model not found')

if __name__ == '__main__': # 현재 파일이 직접 실행될 때만 코드 블록을 실행
    classifier = joblib.load("./model/model.pkl")
    # joblib.load() 함수를 사용하여 저장된 학습된 분류기를 로드. "./model/model.pkl"로 로드할 파일의 경로와 이름을 지정.
    sc = joblib.load("./model/sc.pkl")
    ct = joblib.load("./model/ct.pkl") # joblib.load() 함수를 사용하여 저장된 데이터 전처리기(ct)를 로드.
    print ('Model loaded')
    app.run(debug=True) #  debug=True는 디버그 모드를 활성화하며, 에러 발생 시 디버그 정보를 보여줌.



