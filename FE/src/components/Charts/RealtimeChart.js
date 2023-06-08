import { useEffect, useState } from "react";
import ApexCharts from "apexcharts";

const RealtimeChart = () => {
  const [chart, setChart] = useState(null);

  useEffect(() => {
    let lastDate = 0;
    let data = [];
    const TICKINTERVAL = 86400000;
    const XAXISRANGE = 777600000;

    const getDayWiseTimeSeries = (baseval, count, yrange) => {
      let i = 0;
      while (i < count) {
        const x = baseval;
        const y = Math.floor(Math.random() * (yrange.max - yrange.min + 1)) + yrange.min;

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

      for (let i = 0; i < data.length - 10; i++) {
        data[i].x = newDate - XAXISRANGE - TICKINTERVAL;
        data[i].y = 0;
      }

      data.push({
        x: newDate,
        y: Math.floor(Math.random() * (yrange.max - yrange.min + 1)) + yrange.min
      });
    }

    const resetData = () => {
      data = data.slice(data.length - 10, data.length);
    }

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
        max: 100
      },
      legend: {
        show: false
      }
    };

    const chartOptions = new ApexCharts(document.querySelector("#chart"), options);
    setChart(chartOptions);
    chartOptions.render();

    const updateChartData = () => {
      getNewSeries(lastDate, {
        min: 10,
        max: 90
      });

      chartOptions.updateSeries([
        {
          data: data
        }
      ]);
    };

    const interval = setInterval(updateChartData, 1000);

    return () => {
      clearInterval(interval);
      chartOptions.destroy();
    };
  }, []);

  return <div id="chart" style={{ width: '50%' }} />;
};

export default RealtimeChart;