package com.codefor.dto;

import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
	
	@NotBlank(message = "Full Name is required")
	private String fullname;
	
	@Email(message = "Enter a valid email")
	@NotBlank(message = "Email is required")
	
	private String email;
	
	
	@NotBlank(message = "Password is required")
	@Size(min = 6, message = "Password must be 6 characters..")
	private String password;
	

}
