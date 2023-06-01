# from flask import Flask, request, jsonify
# import joblib
# import pandas as pd
# from preprocessing import preprocess_data
#
# app = Flask(__name__)
#
# # 모델 불러오기
# model = joblib.load('./model/model_knn.pkl')
#
# @app.route('/predict', methods=['POST'])
# def predict():
#     # 스프링부트로부터 받은 JSON 데이터 가져오기
#     data = request.get_json()
#
#     # JSON 데이터를 DataFrame으로 변환
#     df = pd.DataFrame(data)
#
#     # 전처리
#     X = preprocess_data(df)
#
#     # 예측 결과 생성
#     y_pred = model.predict(X)
#
#     # 예측 결과를 JSON 형식으로 반환
#     response = jsonify({'prediction': y_pred.tolist()})
#
#     return response
#
# if __name__ == '__main__':
#     app.run(debug=True)

from flask import Flask, request, jsonify
import joblib
import pandas as pd
from sklearn.preprocessing import StandardScaler

app = Flask(__name__)

# 모델 불러오기
model = joblib.load('./model/model_knn.pkl')

# 전처리 객체 불러오기
scaler = joblib.load('./model/scaler.pkl')

@app.route('/predict', methods=['POST'])
def receive_data():

    # 스프링부트로부터 받은 JSON 데이터 가져오기
    data = request.get_json()
    print(data)
    # JSON 데이터를 DataFrame으로 변환
    df = pd.DataFrame(data)

    # 전처리
    X = preprocess_data(df)

    # 예측 결과 생성
    y_pred = model.predict(X)

    # 예측 결과를 JSON 형식으로 반환
    response = jsonify({'prediction': y_pred.tolist()})

    return response

# def preprocess_data(df):
#     # 전처리
#     X = df[['temperature', 'heartbeat', 'gyroX', 'gyroY', 'gyroZ']]
#     X = scaler.transform(X)
#     return X
def preprocess_data(df):
    # 특성 이름 수정
    df = df.rename(columns={'gyroX': 'GyroX', 'gyroY': 'GyroY', 'gyroZ': 'GyroZ', 'heartbeat': 'Heartbeat', 'temperature': 'Temperature'})

    # 특성 순서 맞추기
    df = df[['Temperature', 'Heartbeat', 'GyroX', 'GyroY', 'GyroZ']]

    # 전처리
    X = scaler.transform(df)
    return X


if __name__ == '__main__':
    app.run(debug=True)

