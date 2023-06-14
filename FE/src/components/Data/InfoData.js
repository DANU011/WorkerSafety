import LineChart from '../Charts/LineChart';
import RealtimeChart from '../Charts/RealtimeChart';
import '../../style/components/InfoData.css';

const InfoData = ({ data, linedata, workerData, detailData }) => {
  // const textColor = data.text === '비정상' ? 'red' : 'black';
  // console.log(workerData);

  return (
    <div className="infoData">
      <div className='data'>
        <p>작업자코드:&nbsp;{workerData.userCode}&nbsp; 이름:&nbsp;{workerData.name}&nbsp; 성별:&nbsp;{workerData.gender}&nbsp; 나이:&nbsp;{workerData.age}&nbsp; 직위:&nbsp;{workerData.role}&nbsp;</p>
        {/* <p style={{ color: textColor }}>상태:&nbsp;{data.text}</p> */}
        {detailData && <p>{detailData.list[0].label}</p>}
      </div>
      <div className='charts'>
        <LineChart linedata={linedata} />
        <RealtimeChart />
      </div>
    </div>
  );
};

export default InfoData;