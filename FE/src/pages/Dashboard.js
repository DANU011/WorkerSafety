import { useState, useEffect } from 'react';
import Map from '../components/Map';
import TableUI from '../components/TableUI';
import DashboardHeader from '../components/DashboardHeader';
import api from '../service/api';
import '../style/Dashboard.css';

const Dashboard = ({loginInfo}) => {
  const [data, setData] = useState([]);

  useEffect(() => {
    // api.get('/api/data')
    //   .then((response) => setData(response.data))
    //   .catch((error) => console.error(error));
    // 테스트용 데이터
    const dummyData = [
      { id: 1, name: '구민지', age: 32, city: 'Busan' },
      { id: 2, name: '김단우', age: 30, city: 'Busan' },
      { id: 3, name: '양철민', age: 25, city: 'Ulsan' },
    ];
    setData(dummyData);
  }, []);

  return (
    <div className='dashboard'>
      <DashboardHeader loginInfo={loginInfo} />
      <div className='container'>
        <div className='map-container'>
          <Map />
        </div>
        <div className='table-container'>
          <TableUI />
        </div>
      </div>
    </div>
  );
};

export default Dashboard;