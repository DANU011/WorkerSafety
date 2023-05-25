# import pickle
from flask import Flask, request, jsonify

app = Flask(__name__)

@app.route('/hello')
def hello_world():
    return 'Hello, World!'

# # pickle 파일을 불러와서 예측 결과를 반환하는 기능
# # 피클 파일 불러오기
# with open('model.pkl', 'rb') as f:
#     model = pickle.load(f)
#
# # 루트 경로 접근시 예측 결과를 반환하는 API
# @app.route("/", methods=["POST"])
# def predict():
#     # POST 요청으로부터 데이터를 가져옴
#     data = request.get_json()
#     # 모델 예측 결과 반환
#     prediction = model.predict(data)
#     # 결과를 JSON 형태로 반환
#     return jsonify({"prediction": prediction.tolist()})

if __name__ == '__main__':
    app.run()
