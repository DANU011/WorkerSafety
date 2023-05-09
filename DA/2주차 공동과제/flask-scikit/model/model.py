import pandas as pd
import numpy as np
from sklearn.impute import SimpleImputer
from sklearn.preprocessing import StandardScaler
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import accuracy_score
import joblib

# 데이터 불러오기
train_df = pd.read_csv('../data/train.csv')
test_df = pd.read_csv('../data/test.csv')

# 데이터 분할
X_train = train_df.iloc[:, :-2].values
y_train = train_df.iloc[:, -1].values
X_test = test_df.iloc[:, :-2].values
y_test = test_df.iloc[:, -1].values

# 결측치 처리
imputer = SimpleImputer(missing_values=np.nan, strategy='mean')
imputer.fit(X_train)
X_train = imputer.transform(X_train)
X_test = imputer.transform(X_test)

# 표준화
scaler = StandardScaler()
X_train = scaler.fit_transform(X_train)
X_test = scaler.transform(X_test)

# 독립변수, 종속변수 설정 -> 독립변수 : 가속도계, 자이로스코프 데이터 / 종속변수 : 활동 레이블
X = X_train
Y = y_train

# 로지스틱 회귀 모델 생성
model = LogisticRegression(max_iter=3000)
model.fit(X, Y)

# 모델 평가
y_pred = model.predict(X_test)
accuracy = accuracy_score(y_test, y_pred)
print('Accuracy:', accuracy)

# 모델과 전처리 객체를 저장
joblib.dump(model, '../model/model_test.pkl')
joblib.dump(scaler, '../model/scaler.pkl')
joblib.dump(imputer, '../model/imputer.pkl')

