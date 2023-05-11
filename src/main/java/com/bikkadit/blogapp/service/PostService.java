package com.bikkadit.blogapp.service;

import java.util.List;

import com.bikkadit.blogapp.exception.PostResponse;
import com.bikkadit.blogapp.payload.PostDto;

public interface PostService {

	// Create Post
	public PostDto createPost(PostDto postdto, Long userId, Long categoryId);

	// Update Post
	public PostDto updatePost(PostDto postdto, Long postId);

	// Get Post
	public PostDto getPost(Long postId);

	// Get All Post
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy,String sortDir);

	// Delete Post
	public void deletePost(Long PostId);

	// Get All Posts By Category
	public List<PostDto> getPostByCategory(Long categoryId);

	// Get All Posts By User
	public List<PostDto> getPostByUser(Long userId);

	// Search Posts
	public List<PostDto> searchPosts(String keyword);
}
