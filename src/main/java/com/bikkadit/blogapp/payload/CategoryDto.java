package com.bikkadit.blogapp.payload;

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
public class CategoryDto {

	private Long categoryId;
	
	@NotEmpty
	@Size(min = 4,message = "Title Invalid min 4 character present")
	private String categoryTitle;
	
	@NotEmpty
	@Size(min = 5,message = "Invalid Description min contain 5 chartacter")
	private String categoryDescription;
	
	
}
