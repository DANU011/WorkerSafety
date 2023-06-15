import { useEffect, useState } from "react";
import ApexCharts from "apexcharts";

const RealtimeChartHeart = ({ workerData, detailData }) => {
  const [chart, setChart] = useState(null);
  const [y, setY] = useState(null);

  useEffect(() => {
    let lastDate = 0;
    let data = [];
    const TICKINTERVAL = 2000;
    const XAXISRANGE = 10000;

    const getDayWiseTimeSeries = (baseval, count, yrange) => {
      let i = 0;
      while (i < count) {
        const x = baseval;
        // const y = Math.floor(Math.random() * (yrange.max - yrange.min + 1)) + yrange.min;
        if (detailData && detailData.list && detailData.list.length > 0 && detailData.list[0].userCode.userCode === workerData.userCode) {
          const y = detailData.list[0].heartbeat;
          setY(y);
        }
        data.push({
          x,
          y
        });
        lastDate = baseval;
        baseval += TICKINTERVAL;
        i++;
      }
    }

    getDayWiseTimeSeries(new Date("11 may 2023 GMT").getTime(), 10, {
      min: 10,
      max: 90
    });

    const getNewSeries = (baseval, yrange) => {
      const newDate = baseval + TICKINTERVAL;
      lastDate = newDate;

      resetData();

      data.push({
        x: newDate,
        // y: Math.floor(Math.random() * (yrange.max - yrange.min + 1)) + yrange.min
        y: detailData.list[0].heartbeat
      });
    };

    const resetData = () => {
      data = data.slice(-10);
    };

    const options = {
      series: [
        {
          data: data.slice()
        }
      ],
      chart: {
        id: "realtime",
        height: 250,
        type: "line",
        animations: {
          enabled: true,
          easing: "linear",
          dynamicAnimation: {
            speed: 1500
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
        curve: "smooth"
      },
      title: {
        text: "Realtime Chart",
        align: "left"
      },
      markers: {
        size: 0
      },
      xaxis: {
        type: "datetime",
        range: XAXISRANGE
      },
      yaxis: {
        min: 45,
        max: 140
      },
      legend: {
        show: false
      }
    };

    const updateChartData = () => {
      if (chart && detailData && detailData.list && detailData.list.length > 0) {
        const firstItem = detailData.list[0];
        const yValue = firstItem.heartbeat;

        // 데이터 갯수 제한
        resetData();

        data.push({
          x: lastDate + TICKINTERVAL, // 마지막 데이터의 시간에 TICKINTERVAL을 더하여 새로운 데이터 시간 설정
          y: yValue
        });

        chart.updateSeries([
          {
            data: data.slice().map(item => ({ x: item.x, y: item.y })) // 복사된 배열 사용
          }
        ]);
      }
    };

    if (detailData && detailData.list && detailData.list.length > 0 && detailData.list[0].userCode.userCode === workerData.userCode) {
      const chartOptions = new ApexCharts(document.querySelector("#chart"), options);
      setChart(chartOptions);
      chartOptions.render();
    }

    const interval = setInterval(updateChartData, TICKINTERVAL);

    return () => {
      clearInterval(interval);
      if (chart) {
        chart.destroy();
      }
    };
  }, [detailData, workerData]);

  return (
    <div>
      {detailData && detailData.list && detailData.list.length > 0 && detailData.list[0].userCode.userCode === workerData.userCode ? (
        <div id="chart1" style={{ width: '100%' }} />
      ) : null}
    </div>
  );
};

export default RealtimeChartHeart;