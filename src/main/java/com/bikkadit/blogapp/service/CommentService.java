package com.bikkadit.blogapp.service;

import java.util.List;

import com.bikkadit.blogapp.payload.CommentDto;

public interface CommentService {

	// Create Comment
	public CommentDto createComment(CommentDto commentDto, Long postId, Long userId);

	// Delete Comment
	public void deleteComment(Long commentId);

	// Update Comment
	public CommentDto updateComment(CommentDto commentDto, Long commentId);

	// Get All Comment
	public List<CommentDto> getAllComment();
}
