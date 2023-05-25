from flask import Flask, request, render_template
import joblib
import numpy as np

app = Flask(__name__)

# Load the model and label encoders
model = joblib.load(open('./model/model_softmax.pkl', 'rb'))
label_encoders = joblib.load(open('./model/model_label_encoders_0517.pkl', 'rb'))

# Route for the main page (home.html)
@app.route('/')
@app.route('/index')
def main():
    return render_template('index.html')

# Route for the prediction page (POST method)
@app.route('/predict', methods=['POST'])
def predict():
    data1 = request.form['a']
    data2 = request.form['b']
    data3 = request.form['c']
    data4 = request.form['d']
    # arr = np.array([[data1, data2, data3, data4]])
    arr = [data1, data2, data3, data4]
    model = joblib.load(open('./model/model_softmax_0517.pkl', 'rb'))
    label_encoders = joblib.load(open('./model/model_label_encoders_0517.pkl', 'rb'))

    def encoding(arr):
        #  일반 list 인코딩 후 모델에 넣을수 있도록 np.array로 형태변환
        encoded_data = []
        for i in range(len(arr)):
            label = list(label_encoders.keys())[i]  # 라벨링할 열의 키
            encoder = label_encoders[label]  # 해당 열의 LabelEncoder 객체
            encoded_value = encoder.transform([arr[i]])[0]  # 데이터 라벨링
            encoded_data.append(encoded_value)

        # 형태변환
        encoded_data = np.array(encoded_data)
        encoded_data = encoded_data.reshape((1, 4))
        return encoded_data

    arr = encoding(arr)
    pred = model.predict(arr)
    # pred = model.predict(encoded_data)
    pred = label_encoders["key2"].inverse_transform(pred)
    return render_template('result.html', data=pred[0])

if __name__ == '__main__':
    app.run(debug=True)

