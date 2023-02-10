package com.pri.auth.payload;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
public class Role {

	@Id
	private int id;
	
	public Role(String name) {
		super();
		this.name = name;
	}

	private String name;
}
