import { useNavigate } from 'react-router-dom';
import LineChart from '../Charts/LineChart';
import { Button } from '@mui/material';
import { styled } from '@mui/styles';

const CustomButton = styled(Button)({
  backgroundColor: '#e9531d',
  color: 'white',
  '&:hover': {
    backgroundColor: '#FF7F50',
  },
});

const InfoData1 = ({ data }) => {
  const navigate = useNavigate();

  const handleButtonClick = () => {
    navigate('/detail', { state: { dataProp: data } });
  };

  return (
    <div className="infoData">
      <div>
        <CustomButton onClick={handleButtonClick} variant="contained">상세보기</CustomButton>
        <LineChart />
      </div>
    </div>
  );
};

export default InfoData1;