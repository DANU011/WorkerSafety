import { useLocation } from 'react-router-dom';

const DataDetail = () => {
  const location = useLocation();
  const data = location.state.dataProp;

  return (
    <>
      <h1>상세보기 {data.text}</h1>
    </>
  );
};

export default DataDetail;