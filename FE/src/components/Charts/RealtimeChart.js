import { useEffect, useRef, useState } from 'react';
import ApexCharts from 'apexcharts';

const RealtimeChart = () => {
  const chartRef = useRef(null);
  const [chart, setChart] = useState(null);
  const [data, setData] = useState([]);

  useEffect(() => {
    const lastDate = Date.now();
    const XAXISRANGE = 86400000;

    const getNewSeries = (baseval, yrange) => {
      const newTime = baseval + 86400000;
      const newData = Math.floor(Math.random() * (yrange.max - yrange.min + 1)) + yrange.min;
      setData((prevData) => [...prevData, { x: newTime, y: newData }]);
    };

    const chartOptions = {
      chart: {
        id: 'realtime',
        height: 250,
        type: 'line',
        animations: {
          enabled: true,
          easing: 'linear',
          dynamicAnimation: {
            speed: 1000
          }
        },
        toolbar: {
          show: false
        },
        zoom: {
          enabled: false
        }
      },
      dataLabels: {
        enabled: false
      },
      stroke: {
        curve: 'smooth'
      },
      title: {
        text: 'Dynamic Updating Chart',
        align: 'left'
      },
      markers: {
        size: 0
      },
      xaxis: {
        type: 'datetime',
        range: XAXISRANGE,
        categories: data.length > 0 ? data.map((point) => point.x) : []
      },
      yaxis: {
        max: 100
      },
      legend: {
        show: false
      }
    };

    if (!chart) {
      const newChart = new ApexCharts(chartRef.current, chartOptions);
      setChart(newChart);
      newChart.render();
    }

    const interval = setInterval(() => {
      getNewSeries(lastDate, { min: 10, max: 90 });
      if (chart) {
        chart.updateSeries([{ data: [...data, { x: lastDate, y: data.length + 1 }] }]);
      }
    }, 1000);

    return () => {
      clearInterval(interval);
    };
  }, [chart, data]);

  return <div id="chart" ref={chartRef} />;
};

export default RealtimeChart;