package com.pri.auth.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Admin {

	private String username;
	private String password;
	private String pass;
}
