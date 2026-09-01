package com.codefor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codefor.dto.LoginRequest;
import com.codefor.dto.LoginResponse;
import com.codefor.dto.RegisterRequest;
import com.codefor.dto.RegisterResponse;
import com.codefor.service.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private final UserService service;
	
	public AuthController(UserService service)
	{
		this.service= service;
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<RegisterResponse> registerUser(@Valid @RequestBody RegisterRequest request)
	{
		RegisterResponse response = service.registerUser(request);
//		User user = service.registerUser(request);
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(
	        @Valid @RequestBody LoginRequest request) {

	    LoginResponse response = service.loginUser(request);

	    return ResponseEntity.ok(response);
	}
	
	

}
