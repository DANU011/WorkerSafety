import requests
from flask import Flask, request, jsonify

app = Flask(__name__)

@app.route('/hello', methods=['POST'])
def hello():
    data = request.get_json()
    name = data.get('name', '')

    return jsonify({"message": f"Hello, {name}!"})

if __name__ == '__main__':
    # 전송할 데이터 생성
    data = {"name": "John"}

    # JSON 형태로 데이터를 전송하는 POST 요청 생성
    response = requests.post('http://localhost:5000/hello', json=data)

    # 서버에서 전송한 응답을 출력
    print(response.json())

    app.run()

