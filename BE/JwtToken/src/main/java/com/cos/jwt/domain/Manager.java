package com.cos.jwt.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Manager {
	@Id
	private String managerid;
	private String password;
	private String name;
	private String role;
}
