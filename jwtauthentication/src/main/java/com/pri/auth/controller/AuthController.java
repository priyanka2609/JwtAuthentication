package com.pri.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pri.auth.config.JwtAuthResponse;
import com.pri.auth.config.JwtTokenHelper;
import com.pri.auth.payload.Admin;
import com.pri.auth.payload.JwtAuthRequest;
import com.pri.auth.payload.Role;
import com.pri.auth.repo.RoleRepo;
import com.pri.auth.repo.UserRepo;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private String globalToken = null;
	
	@PostMapping("/register")
	public ResponseEntity<JwtAuthResponse> createUser(
			@RequestBody JwtAuthRequest request){
		request.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
		Role role = roleRepo.findById(2).get();
		request.setRole(role);
		System.out.println(request);
		@SuppressWarnings("unused")
		JwtAuthRequest save = repo.save(request);
		 return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/register/admin")
	public ResponseEntity<JwtAuthResponse> createAdmin(
			@RequestBody Admin admin){
		
		if (!admin.getPass().equals("qwerty")) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		JwtAuthRequest request = new JwtAuthRequest();
		request.setUsername(admin.getUsername());
		request.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
		Role role = roleRepo.findById(1).get();
		request.setRole(role);
		System.out.println(request);
		repo.save(request);
		 return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(
			@RequestBody JwtAuthRequest request){
		System.out.println("start");
		 this.authenticate(request.getUsername(), request.getPassword());
		 UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		 System.out.println(userDetails);
		 System.out.println("end");
		 String token = this.jwtTokenHelper.generate(userDetails);
		 JwtAuthResponse response = new JwtAuthResponse();
		 response.setToken(token);
		 globalToken = token;
		 System.out.println(globalToken+"/n"+token);
		 return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}


	private void authenticate(String username, String password) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		this.authenticationManager.authenticate(authenticationToken);
	}
}
