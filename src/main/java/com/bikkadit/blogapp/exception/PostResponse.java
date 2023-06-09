package com.bikkadit.blogapp.exception;

import java.util.List;

import com.bikkadit.blogapp.payload.PostDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

	private List<PostDto> content;
	
	private int pageNumber;
	
	private int pageSize;
	
	private Long totalElements;
	
	private int totalPages;
	
	private boolean lastPage;
}
