package com.pri.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	//Allowing access to everyone
	
	  @GetMapping("/public")
	  public ResponseEntity<String> getPublicResource() {
	    return ResponseEntity.ok("This is a public resource");
	  }

	//Access by only registered user:

	  @GetMapping("/user")
	  public ResponseEntity<String> getUserResource() {
	    return ResponseEntity.ok("This is a user resource");
	  }

	//Only accessed by admin
	  
	  @GetMapping("/admin")
	  public ResponseEntity<String> getAdminResource() {
	    return ResponseEntity.ok("This is an admin resource");
	  }
	}

