import LineChart from '../Charts/LineChart';
import RealtimeChart from '../Charts/RealtimeChart';
import '../../style/components/InfoData.css';

const InfoData = ({ data, linedata }) => {
  const textColor = data.text === '비정상' ? 'red' : 'black';

  return (
    <div className="infoData">
      <h1 style={{ color: textColor }}>{data.text}</h1>
      <div className='charts'>
        <LineChart linedata={linedata} />
        <RealtimeChart />
      </div>
    </div>
  );
};

export default InfoData;