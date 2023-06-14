import { useState, useEffect } from 'react';
import LineChart from '../Charts/LineChart';
import RealtimeChart from '../Charts/RealtimeChart';
import api from '../../service/api';
import '../../style/components/InfoData.css';

const InfoData = ({ data, linedata, workerData }) => {
  const [detailData, setDetailData] = useState([]);

  const accessToken = sessionStorage.getItem('accessToken');

  useEffect(() => {
    api.post(
      '/worker/start',
      {},
      {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      }
    )
      .then((response) => {
        console.log(response.data);
      })
      .catch((error) => {
        console.error(error);
        if (error.response.status === 403) {
          window.location.href = '/';
        } else {
          console.error(error);
        }
      });

    const fetchData = async () => {
      try {
        const response = await api.post('/worker/listdetail', {}, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        });
        const detailData = response.data;
        setDetailData(detailData);
      } catch (error) {
        console.error(error);
        if (error.response && error.response.status === 403) {
          window.location.href = '/';
        } else {
          console.error(error);
        }
      }
    };

    const interval = setInterval(fetchData, 1000);

    return () => {
      clearInterval(interval);
    };
  }, [accessToken]);

  console.log(detailData);

  return (
    <div className="infoData">
      <div className='data'>
        <p>작업자코드:&nbsp;{workerData.userCode}&nbsp; 이름:&nbsp;{workerData.name}&nbsp; 성별:&nbsp;{workerData.gender}&nbsp; 나이:&nbsp;{workerData.age}&nbsp; 직위:&nbsp;{workerData.role}&nbsp;</p>
        {detailData.list && detailData.list.length > 0 && detailData.list[0].userCode.userCode === workerData.userCode && (
          <p>{detailData.response.prediction[0]}</p>
        )}
      </div>
      <div className='charts'>
        <LineChart linedata={linedata} />
        <RealtimeChart />
      </div>
    </div>
  );
};

export default InfoData;