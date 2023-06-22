import { useState, useEffect } from 'react';
import api from '../../service/api';
import '../../style/components/InfoData.css';

const InfoData = ({ workerData, detail }) => {
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
        // console.log(detailData);
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

  useEffect(() => {
    const mapData = () => {
      const data = detailData;
      detail(data);
    };

    mapData();
  }, [detailData])

  return (
    <div className="infoData">
      <div className='data'>
        <p>작업자코드:&nbsp;{workerData.userCode}&nbsp; 이름:&nbsp;{workerData.name}&nbsp; 성별:&nbsp;{workerData.gender}&nbsp; 나이:&nbsp;{workerData.age}&nbsp; 직위:&nbsp;{workerData.role}&nbsp;</p>
        {detailData.list && detailData.list.length > 0 && detailData.list.userCode.userCode === workerData.userCode && (
          <p className={detailData.response.prediction[0] === 'fall' ? 'fall' : 'normal'}>{detailData.response.prediction[0]}</p>
        )}
      </div>
      <div className='charts'>
        {detailData.list && detailData.list.length > 0 && detailData.list[0].userCode.userCode === workerData.userCode && (
          <p className='detail'>맥박:&nbsp;{detailData.list[0].heartbeat}&nbsp; 체온:&nbsp;{detailData.list[0].temperature}</p>
        )}
      </div>
    </div>
  );
};

export default InfoData;