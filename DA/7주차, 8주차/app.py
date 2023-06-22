from flask import Flask, jsonify, request
import pandas as pd
import numpy as np
from keras.models import load_model
from sklearn.preprocessing import LabelEncoder

app = Flask(__name__)

# 레이블 인코더 로드
label_encoder = LabelEncoder()
label_encoder.classes_ = np.load('./model/label_encoder_classes_cnn_lstm_g_SGD.npy')

# 모델 로드
model = load_model('./model/loaded_model_cnn_lstm_g_SGD.h5')

@app.route('/predict', methods=['POST'])
def receive_data():
    # 요청으로부터 JSON 데이터를 가져옵니다
    data = request.get_json()
    print('date :', data)
    # JSON 데이터를 DataFrame으로 변환합니다
    df = pd.DataFrame(data)
    print()
    print('df :', df)

    # userCode 컬럼을 업데이트할 데이터프레임 복사
    updated_df = df.copy()

    # userCode 컬럼 업데이트
    for index, row in updated_df.iterrows():
        updated_df.loc[index, 'userCode'] = row['userCode']['userCode']

    # 업데이트된 데이터프레임 확인
    print(updated_df)

    predict_data = []  # 훈련 입력 데이터

    for i in range(0, len(updated_df) - 19, 20):
        # gx, gy, gz 값 가져오기
        gx_values = updated_df.loc[i:i + 19, 'gx'].values
        gy_values = updated_df.loc[i:i + 19, 'gy'].values
        gz_values = updated_df.loc[i:i + 19, 'gz'].values
        predict_data.append(np.transpose([gx_values, gy_values, gz_values]))

    predict_data = np.array(predict_data)

    # 예측
    y_pred = model.predict(predict_data)

    # 예측 결과 디코딩
    y_pred_encoded = np.argmax(y_pred, axis=1)
    y_pred_labels = label_encoder.inverse_transform(y_pred_encoded)

    # 예측 결과 출력
    print('===== y_pred_labels =====')
    print(y_pred_labels)


    # 레코드 인덱스를 추출합니다
    indices = [1, 21, 41, 61, 81, 101, 121, 141]

    # 레코드들을 담을 리스트를 초기화합니다
    records = []

    # 추출한 인덱스에 해당하는 레코드를 가져와 리스트에 추가합니다
    for index in indices:
        try:
            record = {
                'userCode': float(df.loc[index, 'userCode']['userCode']),
                'temp': float(df.loc[index, 'temp']),
                'heartbeat': float(df.loc[index, 'heartbeat']),
                'prediction': y_pred_labels[(index//20)-1]
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