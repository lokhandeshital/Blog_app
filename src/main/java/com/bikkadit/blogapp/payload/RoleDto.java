package com.bikkadit.blogapp.payload;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class RoleDto {

	private Long id;
	
	@NotEmpty
	private String name;
}
