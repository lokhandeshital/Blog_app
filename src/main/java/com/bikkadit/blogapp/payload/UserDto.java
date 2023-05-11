package com.bikkadit.blogapp.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private Long id;
	
	@NotEmpty
	@Size(min = 3,message = "Invalid User Name Must Be Min 3 Character !!")
	private String name;
	
	@Email(message = "Email Address is Not Valid !!")
	private String email;
	
	@NotEmpty
	@Size(min = 3,max = 10,message = "Invalid Password Must be Min 3 & Max 10 !!")
	private String password;
	
	@NotEmpty
	private String about;
	
}
