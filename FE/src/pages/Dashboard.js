import Map from '../components/Map';
import '../style/Dashboard.css';

function Dashboard() {
  const center = { lat: 37.5665, lng: 126.9780 };
  const zoom = 13;

  return (
    <div>
      <div className='map-container'>
        <Map center={center} zoom={zoom} />
      </div>
    </div>
  );
}

export default Dashboard;