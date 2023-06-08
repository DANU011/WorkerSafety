import { useRef, useEffect } from 'react';
import ApexCharts from 'apexcharts';

const LineChart = ({linedata}) => {
  const chartRef = useRef(null);
  // console.log(linedata.data);

  useEffect(() => {
    const options = {
      series: [
        {
          name: 'Series 1',
          data: linedata.data,
        }
      ],
      chart: {
        height: 250,
        type: 'line',
        zoom: {
          enabled: false
        }
      },
      dataLabels: {
        enabled: false
      },
      stroke: {
        curve: 'straight'
      },
      title: {
        text: 'Line Chart',
        align: 'left'
      },
      grid: {
        row: {
          colors: ['#f3f3f3', 'transparent'], // takes an array which will be repeated on columns
          opacity: 0.5
        },
      },
      xaxis: {
        categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep'],
      }
    };

    const chart = new ApexCharts(chartRef.current, options);
    chart.render();

    return () => {
      chart.destroy();
    };
  }, [linedata]);

  return (
    <div className="linechart-container" style={{ width: '50%' }}>
      <div ref={chartRef} />
    </div>
  );
};

export default LineChart;