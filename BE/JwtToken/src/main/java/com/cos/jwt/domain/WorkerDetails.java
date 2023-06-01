package com.cos.jwt.domain;


import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class WorkerDetails {
	@Id
	private Integer no;
	@ManyToOne()
	@JoinColumn(name = "userCode")
	private Worker  userCode;
	private String Label;
	private Integer Heartbeat;
	private Double Temperature;
	private Double GyroX;
	private Double GyroY;
	private Double GyroZ;
	private Double Lat;
	private Double Lng;
	private LocalTime Time;
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	public Worker getUserCode() {
		return userCode;
	}
	public void setUserCode(Worker userCode) {
		this.userCode = userCode;
	}
	public String getLabel() {
		return Label;
	}
	public void setLabel(String label) {
		Label = label;
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
	
	
	public Double getLat() {
		return Lat;
	}
	public void setLat(Double lat) {
		Lat = lat;
	}
	public Double getLng() {
		return Lng;
	}
	public void setLng(Double lng) {
		Lng = lng;
	}
	public LocalTime getTime() {
		return Time;
	}
	public void setTime(LocalTime time) {
		Time = time;
	}
	
	
}
