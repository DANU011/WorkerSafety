import LineChart from '../Charts/LineChart';
import RealtimeChart from '../Charts/RealtimeChart';
import '../../style/components/InfoData.css';

const InfoData = ({ data }) => {

  return (
    <div className="infoData">
      <h1>{data.text}</h1>
      <div className='charts'>
        <LineChart />
        <RealtimeChart />
      </div>
    </div>
  );
};

export default InfoData;