import { useState, useEffect } from 'react';
import Map from '../components/Map';
import TableUI from '../components/TableUI';
import DashboardHeader from '../components/DashboardHeader';
import api from '../service/api';
import '../style/pages/Dashboard.css';

const Dashboard = ({loginInfo}) => {
  // const [data, setData] = useState([]);
  const [tableData, setTableData] = useState(null);

  // useEffect(() => {
  //   api.get('/api/data')
  //     .then((response) => setData(response.data))
  //     .catch((error) => console.error(error));
  // }, []);

  const handleTableData = (value) => {
    setTableData(value);
  }

  return (
    <div className='dashboard'>
      <DashboardHeader loginInfo={loginInfo} />
      <div className='container'>
        {tableData && (
          <div className='map-container'>
            <Map value={tableData} />
          </div>
        )}
        <div className='table-container'>
          <TableUI onValueChange={handleTableData} />
        </div>
      </div>
    </div>
  );
};

export default Dashboard;