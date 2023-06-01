package com.cos.jwt.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RefreshToken {
	@Id
	private String id;
	 
	private String refreshToken;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	 
	 
}
