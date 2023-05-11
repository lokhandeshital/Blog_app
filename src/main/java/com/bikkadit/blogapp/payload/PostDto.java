package com.bikkadit.blogapp.payload;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.bikkadit.blogapp.model.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

	private Long postId;

	@NotEmpty
	@Size(min = 3, message = "Min 3 character is present in title")
	private String title;

	@NotEmpty
	@Size(min = 3, message = "Min 3 Character is present in content")
	private String content;

	private String imageName;

	private Date Addeddate;

	private UserDto user;

	private CategoryDto category;

	private Set<CommentDto> comments = new HashSet<>();

}
