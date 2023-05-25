package com.cos.jwt.domain;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class WorkerDetails {
	@Id
	private Integer No;
	@ManyToOne()
	@JoinColumn(name = "userCode")
	private Worker  userCode;
	private Integer Heartbeat;
	private Double Temperature;
	private Integer Spo2;
	private Double GyroX;
	private Double GyroY;
	private Double GyroZ;
	private Date Time;
	public Integer getNo() {
		return No;
	}
	public void setNo(Integer no) {
		No = no;
	}
	public Worker getUserCode() {
		return userCode;
	}
	public void setUserCode(Worker userCode) {
		userCode = userCode;
	}
	public Integer getHeartbeat() {
		return Heartbeat;
	}
	public void setHeartbeat(Integer heartbeat) {
		Heartbeat = heartbeat;
	}
	public Double getTemperature() {
		return Temperature;
	}
	public void setTemperature(Double temperature) {
		Temperature = temperature;
	}
	public Integer getSpo2() {
		return Spo2;
	}
	public void setSpo2(Integer spo2) {
		Spo2 = spo2;
	}
	public Double getGyroX() {
		return GyroX;
	}
	public void setGyroX(Double gyroX) {
		GyroX = gyroX;
	}
	public Double getGyroY() {
		return GyroY;
	}
	public void setGyroY(Double gyroY) {
		GyroY = gyroY;
	}
	public Double getGyroZ() {
		return GyroZ;
	}
	public void setGyroZ(Double gyroZ) {
		GyroZ = gyroZ;
	}
	public Date getTime() {
		return Time;
	}
	public void setTime(Date time) {
		Time = time;
	}
	
	
}
