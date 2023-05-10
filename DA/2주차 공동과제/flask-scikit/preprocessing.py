import joblib

# 전처리 객체 불러오기
scaler = joblib.load('./model/scaler.pkl')
imputer = joblib.load('./model/imputer.pkl')

def preprocess_data(df):
    # 전처리
    X = df.iloc[:, :-2].values
    X = imputer.transform(X)
    X = scaler.transform(X)
    return X

# if __name__ == '__main__':
#     pass