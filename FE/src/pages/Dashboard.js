import { useState } from 'react';
import Map from '../components/Map';
import TableUI from '../components/TableUI';
import DashboardHeader from '../components/DashboardHeader';
import '../style/pages/Dashboard.css';

const Dashboard = ({loginInfo}) => {
  const [tableData, setTableData] = useState(null);

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