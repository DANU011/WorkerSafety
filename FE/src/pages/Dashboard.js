import Map from '../components/Map';
import '../style/Dashboard.css';

const Dashboard = () => {
  return (
    <div>
      <div className='map-container'>
        <Map />
      </div>
    </div>
  );
}

export default Dashboard;