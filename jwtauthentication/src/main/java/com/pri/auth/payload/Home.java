package com.pri.auth.payload;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {

	@RequestMapping("/welcome")
	public String welcome(){
		String text = "This is a welcome page";
		text += " This page is not allowed for anauthenticated users.";
		return text;
	}
}
