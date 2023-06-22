from flask import Flask, request, jsonify
# from tensorflow.keras.models import load_model
# import tensorflow as tf
import pandas as pd
import numpy as np

app = Flask(__name__)

# # 모델 불러오기
# model_path = b'./model/loaded_model_cnn_lstm_g_SGD.h5'
# model = tf.keras.models.load_model(model_path)




# 시퀀스 길이 설정
sequence_length = 20


@app.route('/predict', methods=['POST'])
def receive_data():
    # 스프링부트로부터 받은 JSON 데이터 가져오기
    data = request.get_json()
    print(data)

    # JSON 데이터를 DataFrame으로 변환
    df = pd.DataFrame(data)

    # # gx, gy, gz 데이터 추출
    # gx = df['gx'].values
    # gy = df['gy'].values
    # gz = df['gz'].values
    #
    # # 시퀀스 생성
    # sequence = create_sequence(gx, gy, gz)
    #
    # # 시퀀스를 3차원 배열로 변환 (샘플 수, 시퀀스 길이, 특성 수)
    # X = np.array([sequence])
    #
    # # 예측 결과 생성
    # y_pred = model.predict(X)

    # user_ID, temp, heartbeat의 첫 번째 레코드 추출
    userCode = float(df['userCode'].values[0])
    temp = float(df['temp'].values[0])
    heartbeat = float(df['heartbeat'].values[0])
    label = str(df['label'].values[0])

    # 예측 결과와 함께 user_ID, temp, heartbeat을 JSON 형식으로 반환
    response = jsonify({
        'user_ID': userCode,
        'temp': temp,
        'heartbeat': heartbeat,
        # 'prediction': y_pred.tolist()
        'prediction': label
    })

    return response


def create_sequence(gx, gy, gz):
    sequence = []
    for i in range(len(gx) - sequence_length + 1):
        sequence.append(np.concatenate((gx[i:i + sequence_length],
                                        gy[i:i + sequence_length],
                                        gz[i:i + sequence_length])))
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


