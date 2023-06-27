# from flask import Flask, request, jsonify
# import pandas as pd
# import numpy as np
# import tensorflow as tf
# from tensorflow import keras
#
# app = Flask(__name__)
#
# # 모델 불러오기
# model_path = './model/loaded_model_cnn_lstm_g_SGD.h5'
# model = keras.models.load_model(model_path)
#
# # 시퀀스 길이 설정
# sequence_length = 20
#
#
# @app.route('/predict', methods=['POST'])
# def receive_data():
#     # 스프링부트로부터 받은 JSON 데이터 가져오기
#     data = request.get_json()
#     print('data', data)
#
#     # JSON 데이터를 DataFrame으로 변환
#     df = pd.DataFrame(data)
#
#     # gx, gy, gz 데이터 추출
#     gx = np.array(df['gx'])
#     gy = np.array(df['gy'])
#     gz = np.array(df['gz'])
#
#     # 시퀀스 생성
#     sequence = create_sequence(gx, gy, gz)  # 수정된 부분
#
#     # 예측 결과 생성
#     y_pred = model.predict(sequence)  # 수정된 부분
#
#     # userCode, temp, heartbeat 추출
#     userCode = df.loc[0, 'userCode']['userCode']
#     temp = df['temp'].values[0]
#     heartbeat = df['heartbeat'].values[0]
#
#     # 예측 결과와 함께 userCode, temp, heartbeat을 JSON 형식으로 반환
#     response = jsonify({
#         'userCode': userCode,
#         'temp': temp,
#         'heartbeat': heartbeat,
#         'prediction': y_pred.tolist()
#     })
#
#     return response
#
#
# def create_sequence(gx, gy, gz):
#     sequence = []
#     for i in range(len(gx) - sequence_length + 1):
#         seq = np.stack((gx[i:i + sequence_length],
#                         gy[i:i + sequence_length],
#                         gz[i:i + sequence_length]), axis=-1)
#         sequence.append(seq)
#     print(sequence)
#     return np.array(sequence)  # 수정된 부분
#
#
# if __name__ == '__main__':
#     app.run(debug=True)


from flask import Flask, jsonify, request
import numpy as np
import pandas as pd
import pickle
from tensorflow.keras.models import load_model
from sklearn.preprocessing import LabelEncoder
#
# # 훈련된 모델과 라벨 인코더를 로드합니다
# model = load_model('./model/loaded_model_cnn_lstm_g_SGD.h5')
# label_encoder = LabelEncoder()
# label_encoder.classes_ = np.load('./model/label_encoder_classes_cnn_lstm_g_SGD.npy', allow_pickle=True)
#
# # Flask 애플리케이션을 생성합니다
# app = Flask(__name__)
#
# @app.route('/predict', methods=['POST'])
# def receive_data():
#     # 요청으로부터 JSON 데이터를 가져옵니다
#     data = request.get_json()
#
#     # JSON 데이터를 DataFrame으로 변환합니다
#     df = pd.DataFrame(data)
#
#     # 예측을 위해 입력 데이터를 준비합니다
#     X_new = []
#     for i in range(0, len(df), 20):
#         gx_values = df.loc[i:i+19, 'gx'].values
#         gy_values = df.loc[i:i+19, 'gy'].values
#         gz_values = df.loc[i:i+19, 'gz'].values
#         X_new.append(np.transpose([gx_values, gy_values, gz_values]))
#     X_new = np.array(X_new)
#
#     # 예측을 수행합니다
#     y_pred = model.predict(X_new)
#
#     # 예측된 라벨값을 디코드합니다
#     y_pred_encoded = np.argmax(y_pred, axis=1)
#     y_pred_labels = label_encoder.inverse_transform(y_pred_encoded)
#
#     # 예측된 라벨값을 JSON 응답으로 반환합니다
#     response = {'predictions': y_pred_labels.tolist()}
#     return jsonify(response)
#
# if __name__ == '__main__':
#     app.run()

#=================================================================================#
# app = Flask(__name__)
#
# @app.route('/predict', methods=['POST'])
# def receive_data():
#     # 요청으로부터 JSON 데이터를 가져옵니다
#     data = request.get_json()
#
#     # JSON 데이터를 DataFrame으로 변환합니다
#     df = pd.DataFrame(data)
#
#     # 첫 번째 레코드의 userCode, temp, heartbeat, label 값을 추출합니다
#     user_code = float(df.loc[0, 'userCode']['userCode'])
#     temp = float(df.loc[0, 'temp'])
#     heartbeat = float(df.loc[0, 'heartbeat'])
#     label = str(df.loc[0, 'label'])
#
#     # userCode, temp, heartbeat, label 값을 포함하는 JSON 응답을 생성합니다
#     response = {
#         'userCode': user_code,
#         'temp': temp,
#         'heartbeat': heartbeat,
#         'prediction': label
#     }
#
#     # JSON 응답을 반환합니다
#     return jsonify(response)
#
# if __name__ == '__main__':
#     app.run()

#===================================================================================#

app = Flask(__name__)

@app.route('/predict', methods=['POST'])
def receive_data():
    # 요청으로부터 JSON 데이터를 가져옵니다
    data = request.get_json()
    print(data)
    # JSON 데이터를 DataFrame으로 변환합니다
    df = pd.DataFrame(data)

    # 레코드 인덱스를 추출합니다
    indices = [0, 20, 40, 60, 80, 100, 120, 140]

    # 레코드들을 담을 리스트를 초기화합니다
    records = []

    # 추출한 인덱스에 해당하는 레코드를 가져와 리스트에 추가합니다
    for index in indices:
        try:
            record = {
                'userCode': float(df.loc[index, 'userCode']['userCode']),
                'temp': float(df.loc[index, 'temp']),
                'heartbeat': float(df.loc[index, 'heartbeat']),
                'prediction': str(df.loc[index, 'label'])
            }
            records.append(record)
        except KeyError:
            continue

    # 레코드들을 포함하는 JSON 응답을 생성합니다
    response = {'list': records}

    # JSON 응답을 반환합니다
    return jsonify(response)




if __name__ == '__main__':
    app.run()









