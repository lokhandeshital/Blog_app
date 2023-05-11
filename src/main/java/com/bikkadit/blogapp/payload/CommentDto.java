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
public class CommentDto {

	private Long id;

	@NotEmpty
	@Size(min = 3, message = "min 3 Character is present in content")
	private String content;
}
