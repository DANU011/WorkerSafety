import { useRef, useEffect } from 'react';

const Map = () => {
  const mapRef = useRef(null);

  useEffect(() => {
    const script = document.createElement('script');
    script.src = `https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=${process.env.REACT_APP_NAVER_MAPS_CLIENT_ID}&submodules=geocoder`;
    script.async = true;
    script.onload = () => {
      const { naver } = window;
      const location = new naver.maps.LatLng(35.235891, 129.076942);
      const mapOptions = {
        center: location,
        zoom: 17,
        zoomControl: true,
        zoomControlOptions: {
          position: naver.maps.Position.TOP_RIGHT,
        },
      };
      const map = new naver.maps.Map(mapRef.current, mapOptions);
      new naver.maps.Marker({
        position: location,
        map,
      });
    };
    document.head.appendChild(script);
  }, []);

  return (
    <div
      ref={mapRef}
      style={{ width: '100%', height: '100%' }}
    />
  );
};

export default Map;