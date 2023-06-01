package com.cos.jwt.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Worker {
	@Id
	@Column(name = "userCode")
	private Double userCode;
	private String name;
	private String gender;
	private Integer age;
	private String role;
	

	public Double getUserCode() {
		return userCode;
	}
	public void setUserCode(Double userCode) {
		this.userCode = userCode;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
}
