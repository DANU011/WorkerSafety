import { useState, useEffect } from 'react';
import Table from '../components/Table';
import Map from '../components/Map';
import api from '../service/api';
import '../style/Dashboard.css';

const Dashboard = () => {
  const [data, setData] = useState([]);

  useEffect(() => {
    // api.get('/api/data')
    //   .then((response) => setData(response.data))
    //   .catch((error) => console.error(error));
    // 테스트용 데이터
    const dummyData = [
    { id: 1, name: 'John Doe', age: 25, city: 'New York' },
    { id: 2, name: 'Jane Doe', age: 30, city: 'Los Angeles' },
    { id: 3, name: 'Bob Smith', age: 35, city: 'Chicago' },
    ];
    setData(dummyData);
  }, []);

  return (
    <div className='dashboard'>
      <h1 className='dashboard-title'>KEEP ME</h1>
      <div className='container'>
        <div className='map-container'>
          <Map />
        </div>
        <div className='table-container'>
          <Table data={data} />
        </div>
      </div>
    </div>
  );
};

export default Dashboard;