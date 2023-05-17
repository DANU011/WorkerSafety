import { useRef, useEffect, useState } from 'react';
import InfoData1 from '../components/Data/InfoData1';
import InfoData2 from '../components/Data/InfoData2';
import InfoData3 from '../components/Data/InfoData3';
import '../style/Map.css';

const Map = ({value}) => {
  const [map, setMap] = useState(null);
  const [selectedMarkerIndex, setSelectedMarkerIndex] = useState(null);
  const [markers, setMarkers] = useState([]);
  const [isInfoDataVisible, setIsInfoDataVisible] = useState(false);
  const [selectedComponent, setSelectedComponent] = useState(null);

  const mapRef = useRef(null);

  useEffect(() => {
    const locations = [
      { lat: 35.235891, lng: 129.076942, component: <InfoData1 /> },
      { lat: 35.235403, lng: 129.076276, component: <InfoData2 /> },
      { lat: 35.235874, lng: 129.077993, component: <InfoData3 /> },
    ];
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

      const markers = locations.map((location, index) => {
        const marker = new naver.maps.Marker({
          position: new naver.maps.LatLng(location.lat, location.lng),
          map: mapInstance,
          icon: {
            content: `<div class='marker'><div class='marker-number'>${value[index]}</div></div>`,
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
  }, []);

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