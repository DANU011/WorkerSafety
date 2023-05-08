import React from "react";
import "../style/Table.css";

const Table = ({ data }) => {
  return (
    <table>
      <thead>
        <tr>
          <th>번호</th>
          <th>이름</th>
          <th>나이</th>
          <th>지역</th>
        </tr>
      </thead>
      <tbody>
        {data.map((item) => (
          <tr key={item.id}>
            <td>{item.id}</td>
            <td>{item.name}</td>
            <td>{item.age}</td>
            <td>{item.city}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default Table;