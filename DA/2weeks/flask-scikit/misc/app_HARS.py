import flask
from flask import Flask, request, render_template
import joblib
import pandas as pd

app_HARS = Flask(__name__)

# 모델과 전처리 객체 불러오기
model = joblib.load('./model/model.pkl')
scaler = joblib.load('./model/scaler.pkl')
imputer = joblib.load('./model/imputer.pkl')

# index 페이지 라우팅

# @app_HARS.route("/")
# def hello_world():
#     return render_template('index.html')
@app_HARS.route("/")
@app_HARS.route("/index")
def index():
    return flask.render_template('index.html')

# API 엔드포인트 정의
@app_HARS.route('/predict', methods=['POST'])
def predict():
    # 요청 데이터 가져오기
    # data = request.json
    user = request.form['users']

    # # Convert data to dataframe
    # df = pd.DataFrame(data)

    # 유저 데이터 CSV 파일에서 불러오기
    csv_path = f'./data/{user}.csv'
    df = pd.read_csv(csv_path)

    # 전처리
    X = df.iloc[:, :-2].values
    X = imputer.transform(X)
    X = scaler.transform(X)

    # 예측 결과 생성
    y_pred = model.predict(X)

    # 결과 반환
    # return jsonify({'prediction': y_pred.tolist()})
    return render_template('result.html', prediction=y_pred)


if __name__ == '__main__':
    app_HARS.run(debug=True)
