import { useEffect } from 'react';

const Map = () => {
  useEffect(() => {
    const { naver } = window;
    const mapOptions = {
      center: new naver.maps.LatLng(37.49988, 127.03856),
      zoom: 17
    };
    const map = new naver.maps.Map('map', mapOptions);
    const marker = new naver.maps.Marker({
      position: new naver.maps.LatLng(37.49988, 127.03856),
      map: map
    });
  }, []);

  return (
    <div id="map" />
  );
};

export default Map;