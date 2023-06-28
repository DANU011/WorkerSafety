package com.cos.api.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class WorkerAnalysis {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer no;
	@ManyToOne()
	@JoinColumn(name = "userCode")
	private Worker  userCode;
	private Integer heartbeat;
	private Double temp;
	private String prediction;
	private LocalDateTime time;
	
	
	public Worker getUserCode() {
		return userCode;
	}
	public void setUserCode(Worker userCode) {
		this.userCode = userCode;
	}
	public Integer getHeartbeat() {
		return heartbeat;
	}
	public void setHeartbeat(Integer heartbeat) {
		this.heartbeat = heartbeat;
	}
	public Double getTemp() {
		return temp;
	}
	public void setTemp(Double temp) {
		this.temp = temp;
	}
	public String getPrediction() {
		return prediction;
	}
	public void setPrediction(String prediction) {
		this.prediction = prediction;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	
	@Override
	public String toString() {
		return "WorkerAnalysis [userCode=" + userCode + ", heartbeat=" + heartbeat + ", temp=" + temp + ", prediction="
				+ prediction + ", time=" + time + ", no=" + no + "]";
	}
	
	
}
