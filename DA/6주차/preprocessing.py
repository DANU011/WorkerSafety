import joblib

# 전처리 객체 불러오기
scaler = joblib.load('./model/scaler.pkl')

def preprocess_data(df):
    # 전처리
    X = df[['Temperature','Heartbeat','GyroX','GyroY','GyroZ']]
    X = scaler.transform(X)
    return X
