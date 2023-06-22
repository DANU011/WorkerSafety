from flask import Flask, request, jsonify
import pandas as pd
import numpy as np
import tensorflow as tf
from tensorflow import keras

app = Flask(__name__)

# 모델 불러오기
model_path = './model/loaded_model_cnn_lstm_g_SGD.h5'
model = keras.models.load_model(model_path)

# # 모델 컴파일 (eager 모드로 실행)
# model.compile(loss='binary_crossentropy', optimizer='sgd', run_eagerly=True)

# 실행 모드 변경
tf.config.run_functions_eagerly(True)

# 시퀀스 길이 설정
sequence_length = 20

@app.route('/predict', methods=['POST'])
def receive_data():
    # 스프링부트로부터 받은 JSON 데이터 가져오기
    data = request.get_json()
    print('data', data)

    # JSON 데이터를 DataFrame으로 변환
    df = pd.DataFrame(data)

    # gx, gy, gz 데이터 추출
    gx = np.array(df['gx'])
    gy = np.array(df['gy'])
    gz = np.array(df['gz'])

    # 시퀀스 생성
    sequence = create_sequence(gx, gy, gz)

    # 시퀀스를 3차원 배열로 변환 (샘플 수, 시퀀스 길이, 특성 수)
    X = np.array(sequence)

    # 예측 결과 생성
    y_pred = model.predict(X)

    # userCode, temp, heartbeat 추출
    userCode = df.loc[0, 'userCode']['userCode']
    temp = df['temp'].values[0]
    heartbeat = df['heartbeat'].values[0]

    # 예측 결과와 함께 userCode, temp, heartbeat을 JSON 형식으로 반환
    response = jsonify({
        'userCode': userCode,
        'temp': temp,
        'heartbeat': heartbeat,
        'prediction': y_pred.tolist()
    })

    return response


def create_sequence(gx, gy, gz):
    sequence = []
    for i in range(len(gx) - sequence_length + 1):
        seq = np.stack((gx[i:i+sequence_length],
                        gy[i:i+sequence_length],
                        gz[i:i+sequence_length]), axis=-1)
        sequence.append(seq)
    print(sequence)
    return sequence



if __name__ == '__main__':
    app.run(debug=True)



#==================================================================================================================#
#==================================================================================================================#
#==================================================================================================================#
#==================================================================================================================#

# from flask import Flask, request, jsonify
# from tensorflow.keras.models import load_model
# import pandas as pd
# import numpy as np
#
# app = Flask(__name__)
#
# # 모델 불러오기
# model = load_model('./model/loaded_model_cnn_lstm_g_SGD.h5')
#
# # 시퀀스 길이 설정
# sequence_length = 20
#
#
# @app.route('/predict', methods=['POST'])
# def receive_data():
#     # 스프링부트로부터 받은 JSON 데이터 가져오기
#     data = request.get_json()
#     print(data)
#
#     # JSON 데이터를 DataFrame으로 변환
#     df = pd.DataFrame(data)
#
#     # gx, gy, gz 데이터 추출
#     gx = df['gx'].values
#     gy = df['gy'].values
#     gz = df['gz'].values
#
#     # 시퀀스 생성
#     sequence = create_sequence(gx, gy, gz)
#
#     # 시퀀스를 3차원 배열로 변환 (샘플 수, 시퀀스 길이, 특성 수)
#     X = np.array([sequence])
#
#     # 예측 결과 생성
#     y_pred = model.predict(X)
#
#     # user_ID, temp, heartbeat의 첫 번째 레코드 추출
#     user_id = float(df['user_ID'].values[0])
#     temp = float(df['temp'].values[0])
#     heartbeat = float(df['heartbeat'].values[0])
#
#     # 예측 결과와 함께 user_ID, temp, heartbeat을 JSON 형식으로 반환
#     response = jsonify({
#         'user_ID': user_id,
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
#         sequence.append(np.concatenate((gx[i:i + sequence_length],
#                                         gy[i:i + sequence_length],
#                                         gz[i:i + sequence_length])))
#     return sequence
#
#
# if __name__ == '__main__':
#     app.run(debug=True)


