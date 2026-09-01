package com.codefor.dto;

import com.codefor.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponse {
	
	private Long id;
	private String fullname;
	private String email;
	private Role role;
	private String message;
	
	

}
