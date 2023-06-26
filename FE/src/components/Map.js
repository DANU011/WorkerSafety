import { useRef, useEffect, useState } from 'react';
import InfoData from '../components/Data/InfoData';
import api from '../service/api';
import '../style/components/Map.css';

const Map = ({ value }) => {
  const [map, setMap] = useState(null);
  const [selectedMarkerIndex, setSelectedMarkerIndex] = useState(0);
  const [markers, setMarkers] = useState([]);
  const [isInfoDataVisible, setIsInfoDataVisible] = useState(true);
  const [selectedComponent, setSelectedComponent] = useState(null);
  const [workerData, setWorkerData] = useState([]);
  const [detailData, setDetailData] = useState(null);

  const mapRef = useRef(null);

  const data = [
    { id: 1, text: '정상' },
    { id: 2, text: '비정상' },
    { id: 3, text: '정상' },
    { id: 4, text: '정상' },
    { id: 5, text: '정상' },
    { id: 6, text: '정상' },
    { id: 7, text: '비정상' },
    { id: 8, text: '정상' },
  ];

  const accessToken = sessionStorage.getItem('accessToken');

  useEffect(() => {
    api.get('/worker/list', {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    })
      .then((response) => {
        const workerData = response.data;
        console.log(workerData);
        setWorkerData(workerData);
      })
      .catch((error) => {
        console.error(error);
        if (error.response.status === 403) {
          window.location.href = '/';
        } else {
          console.error(error);
        }
      });
  }, [accessToken]);

  const handledetailData = (value) => {
    setDetailData(value);
  };

  console.log(detailData);

  useEffect(() => {
    if (workerData.length > 0) {
      const locations = workerData.map((item, index) => ({
        lat: 35.23589 + index * 0.00005,
        lng: 129.07694 + index * 0.00005,
        component: (
          <InfoData
            data={data[index]}
            workerData={item}
            detail={handledetailData}
            // detailData={detailData.list[0].userCode.userCode === index + 1 ? detailData : null}
          />
        ),
      }));
      const intervalDuration = 700;
      const script = document.createElement('script');
      script.src = `https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=${process.env.REACT_APP_NAVER_MAPS_CLIENT_ID}&submodules=geocoder`;
      script.async = true;
      script.onload = () => {
        const { naver } = window;
        const mapOptions = {
          center: new naver.maps.LatLng(locations[0].lat, locations[0].lng),
          zoom: 17,
          zoomControl: true,
          zoomControlOptions: {
            position: naver.maps.Position.TOP_RIGHT,
          },
        };
        const mapInstance = new naver.maps.Map(mapRef.current, mapOptions);
        setMap(mapInstance);

        const selectedLocation = locations[selectedMarkerIndex];
        setSelectedComponent(selectedLocation.component);
        setIsInfoDataVisible(true);

        const markers = locations.map((location, index) => {
          const markerColor = location.component.props.data.text === '비정상' ? 'red' : 'black';
          const marker = new naver.maps.Marker({
            position: new naver.maps.LatLng(location.lat, location.lng),
            map: mapInstance,
            icon: {
              content: `<div class='marker' style='color: ${markerColor};'><div class='marker-number'>${value[index]}</div></div>`,
              size: new naver.maps.Size(30, 40),
              anchor: new naver.maps.Point(15, 40),
            },
          });
          marker.addListener('click', () => {
            setSelectedMarkerIndex(index);
            setSelectedComponent(location.component);
            setIsInfoDataVisible(true);
          });

          return marker;
        });
        setMarkers(markers);

        setInterval(() => {
          markers.forEach((marker, index) => {
            const position = marker.getPosition();
            const newPosition = new naver.maps.LatLng(
              position.lat() + Math.random() * 0.0002 - 0.0001,
              position.lng() + Math.random() * 0.0002 - 0.0001
            );
            marker.setPosition(newPosition);
          });
        }, intervalDuration);
      };

      document.head.appendChild(script);
    }
  }, [workerData]);

  // useEffect(() => {
  //   const timeoutId = setTimeout(() => {
  //     if (markers.some((marker) => marker.getIcon().content.includes('red'))) {
  //       alert('위험 작업자가 있습니다!');
  //     }
  //   }, 5000);

  //   return () => {
  //     clearTimeout(timeoutId);
  //   };
  // }, [markers]);

  const handleCloseInfoData = () => {
    setIsInfoDataVisible(false);
  };

  return (
    <div style={{ width: '100%', height: '100%' }}>
      <div ref={mapRef} style={{ width: '100%', height: '100%' }}></div>
      {isInfoDataVisible && (
        <div>
          {selectedComponent}
          <button onClick={handleCloseInfoData}>Close</button>
        </div>
      )}
    </div>
  );
};

export default Map;