import { useState, useEffect } from 'react';
import api from '../../service/api';
import '../../style/components/InfoData.css';
import Chart from 'react-apexcharts';

const InfoData = ({ workerData, detail }) => {
  const [detailData, setDetailData] = useState([]);
  const [heartbeatData, setHeartbeatData] = useState([]);
  const [tempData, setTempData] = useState([]);
  
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
        const heartbeatValues = detailData.list.map((item) => item.heartbeat);
        setHeartbeatData(heartbeatValues);
        const tempValues = detailData.list.map((item) => item.temp);
        setTempData(tempValues);
      } catch (error) {
        console.error(error);
        if (error.response && error.response.status === 403) {
          window.location.href = '/';
        } else {
          console.error(error);
        }
      }
    };

    const interval = setInterval(fetchData, 2000);

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

  const optionsH = {
    chart: {
      id: 'heartbeat-chart',
    },
    xaxis: {
      categories: [2, 7, 8, 4, 1, 3, 5, 6],
      title: {
        text: '작업자코드',
      },
    },
    yaxis: {
      title: {
        text: 'Heartbeat',
      },
      min: 0,
    },
    stroke: {
      curve: 'straight',
    },
  };

  const optionsT = {
    chart: {
      id: 'temp-chart',
    },
    xaxis: {
      categories: [2, 7, 8, 4, 1, 3, 5, 6],
      title: {
        text: '작업자코드',
      },
    },
    yaxis: {
      title: {
        text: 'Temp',
      },
      min: 0,
    },
    stroke: {
      curve: 'straight',
    },
  };

  const seriesH = [
    {
      name: 'Heartbeat',
      data: heartbeatData,
    },
  ];

  const seriesT = [
    {
      name: 'Temp',
      data: tempData,
    },
  ];

  return (
    <div className="infoData">
      <div className='data'>
        <p>작업자코드:&nbsp;{workerData.userCode}&nbsp; 이름:&nbsp;{workerData.name}&nbsp; 성별:&nbsp;{workerData.gender}&nbsp; 나이:&nbsp;{workerData.age}&nbsp; 직위:&nbsp;{workerData.role}&nbsp;</p>
        {detailData.list && detailData.list.length > 0 && detailData.list.map((item, index) => (
          item.userCode === workerData.userCode && (
            <p key={index} className={item.prediction === 'fall' ? 'fall' : 'normal'}>
              {item.prediction}
            </p>
          )
        ))}
      </div>
      <div className='charts'>
        {detailData.list && detailData.list.length > 0 && detailData.list.map((item, index) => (
          item.userCode === workerData.userCode && (
            <p key={index} className={`detail ${item.heartbeat >= 140 || item.heartbeat <= 50 || item.temp >= 37 || item.temp <= 35 ? 'abnormal' : ''}`}>맥박:&nbsp;{item.heartbeat}&nbsp; 체온:&nbsp;{item.temp}</p>
          )
        ))}
        {heartbeatData.length > 0 && (
          <div className="chart">
            <Chart
              options={optionsH}
              series={seriesH}
              type="line"
              width="330"
              height="250"
            />
            <Chart
              options={optionsT}
              series={seriesT}
              type="line"
              width="330"
              height="250"
            />
          </div>
        )}
      </div>
    </div>
  );
};

export default InfoData;