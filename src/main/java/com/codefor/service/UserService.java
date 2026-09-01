package com.codefor.service;

import com.codefor.dto.LoginRequest;
import com.codefor.dto.LoginResponse;
import com.codefor.dto.RegisterRequest;
import com.codefor.dto.RegisterResponse;

public interface UserService {
//	User registerUser(RegisterRequest request);
	
	//replace by

	RegisterResponse registerUser(RegisterRequest request);
	
	LoginResponse loginUser(LoginRequest request);
}
